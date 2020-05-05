class HeaderView{
    displayHeader(){
        const user = window.users.getUser(window._username);
        const usernameContainer = document.getElementById("header-username");
        const logOutInButton = document.getElementById("log-out-btn");
        const imageContainer = document.getElementById("user-photo-container");
        if (!user){
            usernameContainer.innerText = "";
            logOutInButton.innerHTML = `
                <img class="header-icons" src="resources/img/log-in.png" title="Log in" alt="Log in" onclick="window.header.login('@asenyarb')">
            `;
            imageContainer.innerHTML = `
                <img class="user-photo" src="resources/img/user-photo.jpg" alt="New user photo">
            `;
        } else {
            usernameContainer.innerText = user.userName;
            logOutInButton.innerHTML = `
                <img class="header-icons" src="resources/img/log-out.png" title="Log out" alt="Log out" onclick="window.header.logout()">
            `;
            imageContainer.innerHTML = `
                <img class="user-photo" src="${user.userPhoto}" alt="User photo">
            `;
        }
    }

    logout(){
        window._username = "";
        this.displayHeader();
        window.view.redisplay();
    }

    login(username="@asenyarb"){
        window._username = username;
        this.displayHeader();
        window.view.redisplay();
    }
}

(() => {
    window.header = new HeaderView();
})()