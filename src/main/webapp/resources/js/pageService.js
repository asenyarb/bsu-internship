class PageService{
    initializePage(){
        const usernameContainer = document.getElementById("header-username");
        const logOutInButton = document.getElementById("log-out-btn");
        const imageContainer = document.getElementById("user-photo-container");
        if (!window._user){
            usernameContainer.innerText = "";
            logOutInButton.innerHTML = `
                <img class="header-icons" src="resources/img/log-in.png" title="Log in" alt="Log in" onclick="window.popups.showLoginDialog()">
            `;
            imageContainer.innerHTML = `
                <img class="user-photo" src="resources/img/user-photo.jpg" alt="New user photo">
            `;
        } else {
            usernameContainer.innerText = window._user.userName;
            logOutInButton.innerHTML = `
                <img class="header-icons" src="resources/img/log-out.png" title="Log out" alt="Log out" onclick="window.pageService.logout()">
            `;
            imageContainer.innerHTML = `
                <img class="user-photo" src="${window._user.userPhoto}" alt="User photo">
            `;
        }
        document.getElementById("add-post-btn").addEventListener("click", e => {
            window.popups.showCreatePostDialog();
            e.stopPropagation();
        });

        document.getElementsByClassName("load-more-btn").item(0)
            .addEventListener(
                "click",
                () => {
                    window.view.displayPosts(window._postsFrom);
                }
            )

        window.posts.restore();

        this.initializeFilter();
    }

    logout(){
        window._user = null;
        this.initializePage();
        window.view.redisplay();
    }

    login(username, password){
        const user = window.users.login(username, password);
        if (!user){
            return false;
        }
        window._user = user;
        this.initializePage();
        window.view.redisplay();
        return true;
    }

    initializeFilter(){
        document.getElementById("filter-form").innerHTML = `
        <legend><i class="fa fa-filter"></i>Filter</legend>
        <div class="filter-fields">
        <div class="filter-formset">
        <input type="text" class="filter-username filter-textarea" placeholder="@username">
        <input type="text" class="filter-tags filter-textarea" placeholder="tag">
        </div>
        <div class="filter-formset">
        <input type="date" class="filter-date-from filter-textarea">
        <input type="date" class="filter-date-to filter-textarea">
        </div>
        </div>
        <input type="submit" class="filter-submit" value="OK">
        `;
        this.setFilterEvent();
    }

    setFilterEvent(){
        const filterForm = document.getElementById("filter-form");
        filterForm.onsubmit = () => {return false;}
        filterForm.addEventListener(
            "submit",
            () => {
                const username = filterForm.getElementsByClassName("filter-username")
                    .item(0).value;
                const tags = filterForm.getElementsByClassName("filter-tags")
                    .item(0).value;
                const dateFrom = new Date(
                    filterForm.getElementsByClassName("filter-date-from")
                        .item(0).valueAsNumber
                );
                const dateTo = new Date(filterForm.getElementsByClassName("filter-date-to")
                    .item(0).valueAsNumber
                );
                const filterConfig = [];
                if (username){
                    filterConfig.push({
                        fieldName: "userName",
                        fieldValue: username
                    })
                }
                if (tags){
                    filterConfig.push({
                        fieldName: "postTags",
                        fieldValue: tags.split(",")
                    })
                }
                if (!isNaN(dateFrom.getDate())){
                    filterConfig.push({
                        fieldName: "date",
                        fieldValue: dateFrom,
                        comparison: "after"
                    })
                }
                if (!isNaN(dateTo.getDate())){
                    filterConfig.push({
                        fieldName: "date",
                        fieldValue: dateTo,
                        comparison: "before"
                    })
                }
                window._filterConfig = filterConfig
                window.view.redisplay();
                window.pageService.initializeFilter();
                switchFilter();
            }
        )
    }
}

(() => {
    window.pageService = new PageService();
})()