class PostConstructor{
    getLikeDiv(post, user){
        if (!user) {
            return '';
        } else if (post.postLikes.find(username => user.userName === username)){
            return `
                <div class="action-area-btn like">
                <button class="like-btn" id="like-btn">
                    <img src="resources/img/red_heart.png"></img>
                </button>
                </div>
        `;
        } else {
            return `
                <div class="action-area-btn like">
                <button class="like-btn" id="like-btn">
                    <img src="resources/img/heart_regular.png"></img>
                </button>
                </div>
        `;
        }
    }

    getEditDeleteDiv(post, user) {
        if (!user || post.userName !== user.userName){
            return '';
        } else {
            return `
            <div class="edit">
            <button class="edit-btn">
                <img src="resources/img/edit_regular.png"></img>
            </button>
            </div>
            <div class="delete">
            <button class="trash-btn">
                <img src="resources/img/trash.png"></img>
            </button>
            </div>
        `
        }
    }


    getPostAsHTML(post, user){
        const postDate = post.date;
        const date = parseValue(postDate.getHours()) + ":"
            + parseValue(postDate.getMinutes()) + " " + parseValue(postDate.getDate())
            + "." + parseValue(postDate.getMonth()) + "." + parseValue(postDate.getFullYear());

        const like_div = this.getLikeDiv(post, user);
        const edit_delete_div = this.getEditDeleteDiv(post, user);

        return `
                <div class="user-photo-column">
                    <img class="user-photo" src="${post.userPhoto}">
                </div>
                <div class="container-column">
                <div class="post-info">
                    <div class="username-in-post">
                        <h4 class="username">${post.userName}</h4>
                    </div>
                    <time class="date-and-time">
                        <p>${date}</p>
                    </time>
                </div>
                <div class="post-text">
                    <p>
                        ${post.postText}
                    </p>
                </div>
                <div class="action-area">
                    <div class="post-tags-container">
                    <p class="post-tags">${this.displayTags(post.postTags)}</p>
                    </div>
                    ${edit_delete_div}${like_div}
                </div>
                </div>
                `
    }

    setPostEventListeners(postElement, post){
        const like = postElement.getElementsByClassName("like").item(0);
        if (like) {
            like.addEventListener(
                "click",
                e => {
                    window.view.like_pressed(post.id);
                    e.stopPropagation();
                }
            );
        }

        const deleteElement = postElement.getElementsByClassName("delete").item(0);
        if (deleteElement){
            deleteElement.addEventListener(
                "click",
                e => {
                    window.popups.showDeleteDialog(post.id);
                    e.stopPropagation();
                }
            )
        }

        const editElement = postElement.getElementsByClassName("edit").item(0);
        if (editElement){
            editElement.addEventListener(
                "click",
                e => {
                    window.popups.showEditPostDialog(post);
                    e.stopPropagation();
                }
            )
        }
    }

    getPostAsDOMElement(post, user){
        let postElement = document.createElement("article");

        postElement.id = `post-${post.id}`;
        postElement.innerHTML = this.getPostAsHTML(post, user);
        this.setPostEventListeners(postElement, post);
        return postElement;
    }

    displayTags(tags){
        const s = tags.toString();
        return s.replace(/,/g, " ");
    }
}

(() => {
    window.postConstructor = new PostConstructor();
})()