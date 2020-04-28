class Controller{
    set_like(post, username){
        const userIndexInLikes = post.postLikes.indexOf(username);
        if (userIndexInLikes !== -1){
            post.postLikes.splice(userIndexInLikes, 1);
        } else {
            post.postLikes.push(username);
        }
    }

    delete(id){
        window.posts.remove(id);
    }
}

(() => {
    window.controller = new Controller();
})()
