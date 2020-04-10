let posts = [];

standartPostFields = ['id', 'userPhoto', 'userName', 'date', 'postText', 'postPhotos', 'postTags', 'postLikes'];

function filterPostsBy(config) {
    let result = posts;
    config.forEach(configElement => {
        result = result.filter(element => {
            const elementToFilter = element[configElement.fieldName];

            if (Array.isArray(elementToFilter)) {
                // Array intersection is not empty
                return elementToFilter.filter(element => {
                    return configElement.fieldValue.includes(element);
                }).length > 0;

            } else if (elementToFilter instanceof Date) {
                if (configElement.comparison === 'before') {
                    return elementToFilter < configElement.fieldValue;
                } else if (configElement.comparison === 'after') {
                    return elementToFilter > configElement.fieldValue;
                }
            } else {
                return configElement.fieldValue.includes(elementToFilter);
            }
        });
    });
    return result;
}

function sortByDate(postsToSort) {
    postsToSort.sort((a, b) => {
        return b.date - a.date;
    })
    return postsToSort;
}

function getPosts(skip = 0, top = 10, filterConfig = []) {
    const filteredPosts = filterPostsBy(filterConfig);
    const slicedPosts = filteredPosts.slice(skip, skip + top);
    return sortByDate(slicedPosts);
}

function getPost(id, postsToSearchIn = posts) {
    const matchingPosts = postsToSearchIn.filter(element => {
        return element.id === id;
    })
    if (matchingPosts.length > 1) {
        throw Error(`Got ${matchingPosts.length} posts with id = '${id}' instead of 1!`);
    } else if (matchingPosts.length == 0) {
        throw Error(`No post with id = '${id}' was found!`);
    }
    return matchingPosts[0];
}

function postDoesNotContainExtraFields(postObject) {
    Object.keys(postObject).forEach(field => {
        if (standartPostFields.indexOf(field) == -1) {
            return false;
        }
    }
    )
    return true;
}

function validatePost(postObject, newPost = true) {
    return (
        postDoesNotContainExtraFields(postObject) &&
        (typeof postObject.id === "string") &&
        (!newPost || !posts.find((e) => { return e.id == postObject.id; })) &&
        (typeof postObject.postText === 'string') &&
        //postObject.postText.length < 200 &&
        (postObject.date instanceof Date) &&
        (typeof postObject.userName === 'string') &&
        postObject.userName.length &&
        (typeof postObject.userPhoto === 'string')
    );
}

function addPost(post) {
    if (validatePost(post)) {
        posts.push(post);
        return true;
    }
    return false;
}

function removePost(id) {
    // Add deletion by other fields in next task implementation
    const removedPosts = [];
    let removedPostsNumber = 0;
    posts = posts.filter(el => {
        const equal = (el.id === id);
        if (equal) {
            removedPosts.push(el);
            removedPostsNumber += 1;
        }
        return !equal;
    })
    return (removedPostsNumber, removedPosts);
}

function editPost(id, newPostFields) {
    const oldPost = getPost(id);
    const fieldsToChange = Object.keys(newPostFields);
    fieldsToChange.forEach(field => {
        oldPost[field] = newPostFields[field];
    });
    if (validatePost(oldPost, false)) {
        posts[posts.findIndex(post => { return post.id == id })] = oldPost;
    } else {
        throw Error(`Unable to edit post fields with these values: ${newPostFields}`);
    }
}


(function () {
    const _api = {}

    _api.filterPostsBy = filterPostsBy;
    _api.getPosts = getPosts;
    _api.getPost = getPost;
    _api.validatePost = validatePost;
    _api.addPost = addPost;
    _api.removePost = removePost;
    _api.editPost = editPost;

    window.api = _api;
})();