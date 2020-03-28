const config = [
    {
        fieldName: "userName",
        fieldValue: ["@asenyarb", "@milky_way"]
    },
    {
        fieldName: "postTags",
        fieldValue: ["#Interesting", "#tennis"]
    },
    {
        fieldName: "date",
        fieldValue: new Date("2020-02-26"),
        comparison: 'before'
    }
];

const newPosts = window.api.getPosts(0, 10, config);

const config2 = [
    {
        fieldName: "postLikes",
        fieldValue: ["@asenyarb"]
    }
];

const newPosts2 = window.api.getPosts(0, 10, config2);

// const addInvalidPostResult = addPost({});

const addValidPostResult = window.api.addPost(
    {
        id: '45',
        userPhoto: "photos/comp.jpg",
        userName: "@prog_mid",
        date: new Date("2020-02-01T01:21"),
        postText: 'asdasd\
            ',
        postPhotos: [],
        postTags: ["#ASD"],
        postLikes: ["@asenyarb", "@milky_way"]
    }
)

const postsWithAddedPost = window.api.getPosts();

window.api.editPost('45', { postLikes: ["@asenyarb"], postText: "asdasdadadadasdadas" });

const postsWithEditedAddedPost = window.api.getPosts();

const editedNewPost = window.api.getPost('45');

const removedPost = window.api.removePost('10');