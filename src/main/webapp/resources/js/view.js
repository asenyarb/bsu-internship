function parseValue(value){
    return value < 10 ? "0" + value : value;
}

function getLikeDiv(post, user){
    if (!user) {
        return '';
    } else if (post.postLikes.find(username => user.userName === username)){
        return `
                <button class="like-btn">
                    <img src="resources/img/red_heart.png"></img>
                </button>
        `;
    } else {
        return `
                <button class="like-btn">
                    <img src="resources/img/heart_regular.png"></img>
                </button>
        `;
    }
}

function getEditDeleteDiv(post, user) {
    if (!user || post.userName !== user.userName){
        return '';
    } else {
        return `
            <button class="edit-btn">
                <img src="resources/img/edit_regular.png"></img>
            </button>
            <button class="trash-btn">
                <img src="resources/img/trash.png"></img>
            </button>
        `
    }
}


function getPostAsHTML(post, user){
    const postDate = post.date;
    console.log(post.date);
    const date = parseValue(postDate.getHours()) + ":"
        + parseValue(postDate.getMinutes()) + " " + parseValue(postDate.getDate())
        + "." + parseValue(postDate.getMonth()) + "." + parseValue(postDate.getFullYear());

    const like_div = getLikeDiv(post, user);
    const edit_delete_div = getEditDeleteDiv(post, user);

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
                <div class="posts-text">
                    <p>
                        ${post.postText}
                    </p>
                </div>
                <div class="action-area">
                <div class="like-edit-panel">
                ${edit_delete_div}${like_div}
                </div>
                </div>
                </div>
                `
}

function getPostAsDOMElement(post, user){
    let postElement = document.createElement("article");

    postElement.id = "post-" + post.id;
    postElement.innerHTML = getPostAsHTML(post, user);
    return postElement;
}

class PostsView{
    displayPosts(){
        const user = window.users.getUser(window._username);
        console.log(user);
        let el = document.getElementById("posts-container");
        window.posts.getPage().forEach(
            post => {
                const postElement = getPostAsDOMElement(post, user);
                el.append(postElement);
            }
        );
    }

    redisplay(){
        let el = document.getElementById("posts-container");
        el.innerHTML = "";
        this.displayPosts();
    }

    addPost(post){
        if (window.posts.add(post)) {
            // Should re-get posts as we need to resort them
            this.redisplay();
        } else {
            console.log(`Post ${post} isn't valid.`);
        }
    }

    editPost(id, newPostFields){
        try {
            const post = window.posts.edit(id, newPostFields);
            const user = window.users.getUser(window._username);
            document.getElementById(`post-${id}`).innerHTML = getPostAsHTML(post, user);
        } catch (e) {
            console.log(e);
        }
    }

    removePost(id){
        window.posts.remove(id);
        document.getElementById(`post-${id}`).remove();
    }
}

(() => {
    window.view = new PostsView();
})()