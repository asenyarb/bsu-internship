let posts = [
    {
        id: '10',
        user: {
            photo: "photos/user-photo.jpg",
            name: "@asenyarb"
        },
        date: new Date("2020-03-01T14:45"),
        post: {
            text: 'Nabi Tajima was until recently the oldest person in the world. She was born in August 1900, \
            which made her the last person born in the 19th century. \
            She passed away in a hospital in southern Japan at the age of 117. \
            She had more than 160 descendants, including great-great-great grandchildren. \
            Mrs Tajima was the 3rd oldest person in recorded human history. \
            ',
            photos: [],
            tags: ["#interesting", "#Japan"],
            likes: ['@milky_way']
        },
    },
    {
        id: '9',
        user: {
            photo: "photos/planet.jpg",
            name: "@milky_way"
        },
        date: new Date("2020-02-29T11:00"),
        post: {
            text: 'Two Australian police officers were out in the ocean, and they were conducting safety and \
            compliance checks when a great white shark suddenly swam up to their small boat. \
            Luckily, the heart-stopping meeting ended quickly when the shark swam away.\
            ',
            photos: [],
            tags: ["#shark"],
            likes: ['@asenyarb']
        },
    },
    {
        id: '8',
        user: {
            photo: "photos/comp.jpg",
            name: "@prog_mid"
        },
        date: new Date("2020-02-27T06:36"),
        post: {
            text: 'More than 71 people are dead, 17 missing, and more than 40,000 were displaced after a massive \
            storm struck the Philippines. One of the country’s officials said that the storm was not \
            strong enough to be rated as a typhoon, so many people were unprepared. \
            Dozens of them died in landslides and flooding caused by the heavy rains. \
            The country already suffered a number of deadly storms recently – Super \
            Typhoon Mangkhut killed more than 80 people in September and Typhoon Yutu killed 17 more.\
            ',
            photos: [],
            tags: ["#typhoon"],
            likes: ['@milky_way', '@asenyarb']
        },
    },
    {
        id: '7',
        user: {
            photo: "photos/user-photo.jpg",
            name: "@asenyarb"
        },
        date: new Date("2020-02-25T17:30"),
        post: {
            text: 'In recent years, Australia’s south west state of Victoria saw a rapid increase of koalas, \
            meaning that the animals have been literally eating themselves out of house and home by \
            destroying gum trees. Their binging and booming population eventually caused them to starve. \
            Environmentalists have moved 500 koalas over the past year, and their objective now is to \
            control them and keep their numbers down. They gave the females contraceptive implants and then \
            released them into other forests.\
            ',
            photos: [],
            tags: ["#Australia", "#koalas"],
            likes: ['@prog_mid']
        },
    },
    {
        id: '6',
        user: {
            photo: "photos/comp.jpg",
            name: "@prog_mid"
        },
        date: new Date("2020-02-20T05:56"),
        post: {
            text: 'One farmer from Kansas, USA decided to create cow art. He used his cattle, \
            a feed truck and drones to create a huge smiley face out of cows. \
            If that is not enough to make you smile, the farmer and artist is also known for \
            using his trombone to herd the cows, saying that they have happy cows in Kansas. \
            ',
            photos: [],
            tags: ["#cow", "#art"],
            likes: []
        },
    },
    {
        id: '5',
        user: {
            photo: "photos/user-photo.jpg",
            name: "@asenyarb"
        },
        date: new Date("2020-02-15T07:30"),
        post: {
            text: 'Amazon is working on rules about technology which can recognize a person’s face. Amazon’s CEO, \
            Jeff Bezos, says that his team is working on these rules that he wants to present to Congress. \
            Bezos believes that facial recognition has many benefits, but there is also danger that it \
            can be abused.\
            ',
            photos: [],
            tags: ["#Amazon", "#facial_recognition", "#Interesting"],
            likes: []
        },
    },
    {
        id: '4',
        user: {
            photo: "photos/planet.jpg",
            name: "@milky_way"
        },
        date: new Date("2020-02-10T21:21"),
        post: {
            text: 'We’ve all heard the saying ‘painting on a smile’. Well one Japanese artist has taken \
            that phrase to a whole new level. Nobumichi Asai has found a way to project and map \
            expressions onto people’s faces, effectively creating a facial mask. He and his team \
            displayed their three-dimensional facial projection mapping system to a select group of \
            spectators in Tokyo!\
            ',
            photos: [],
            tags: ["#Japan"],
            likes: ["@asenyarb"]
        },
    },
    {
        id: '3',
        user: {
            photo: "photos/user-photo.jpg",
            name: "@asenyarb"
        },
        date: new Date("2020-02-05T05:30"),
        post: {
            text: 'At the 2018 Winter Olympics, the competition and the entertainment was heating up! Some \
            fire artists held a special show on a beach of a coastal city that is helping with the \
            Pyeongchang Games in South Korea. \
            Visitors braved the cold outside to watch the sparks fly in the show. The fire artists \
            came with other artists to celebrate the Olympics.\
            ',
            photos: [],
            tags: ["#South_Korea", "#Olympics", "#Interesting"],
            likes: []
        },
    },
    {
        id: '2',
        user: {
            photo: "photos/planet.jpg",
            name: "@milky_way"
        },
        date: new Date("2020-02-07T14:00"),
        post: {
            text: 'Maria Sharapova is a tennis player. She comes from Russia. She wins an important match with \
            the best tennis player in the world. That player is Serena Williams. \
            Sharapova becomes very famous after this match. She is only 17 years old.\
            ',
            photos: [],
            tags: ["#tennis"],
            likes: []
        },
    },
    {
        id: '1',
        user: {
            photo: "photos/comp.jpg",
            name: "@prog_mid"
        },
        date: new Date("2020-02-01T01:29"),
        post: {
            text: 'Malcolm X is a famous US civil rights leader. 55 years ago, someone shoots him in New \
            York City, USA. Malcolm X is born Malcolm Little in 1925. He changes his name after he \
            changes his religion to Islam.\
            ',
            photos: [],
            tags: ["#NY"],
            likes: ["@asenyarb", "@milky_way"]
        },
    }
];

function filterPostsBy(config) {
    let result = posts;
    config.forEach(configElement => {
        result = result.filter(element => {
            const pathToField = configElement.fieldName.split(".");
            let elementToFilter = element;
            // get *вложенное* field in post object
            pathToField.forEach(field => {
                elementToFilter = elementToFilter[field];
            })

            if (Array.isArray(elementToFilter)) {
                // Array intersection is not empty
                return elementToFilter.filter(element => {
                    return configElement.fieldValue.includes(element);
                }).length > 0;

            } else if (elementToFilter instanceof Date) {
                if (configElement.comparison === 'lower') {
                    return elementToFilter < configElement.fieldValue;
                } else if (configElement.comparison === 'higher') {
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
        // exception
    }
    return matchingPosts[0];
}

function validatePost(postObject) {
    return (
        (typeof postObject.id === "string") &&
        !posts.find((e) => { return e.id == postObject.id; }) &&
        postObject.post &&
        (typeof postObject.post.text === 'string') &&
        postObject.post.text.length < 200 &&
        (postObject.date instanceof Date) &&
        postObject.user &&
        (typeof postObject.user.name === 'string') &&
        postObject.user.name.length &&
        (typeof postObject.user.photo === 'string')
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
    let removedPost = {};
    posts = posts.filter((el) => {
        const equal = (el.id === id);
        if (equal) {
            removedPost = el;
        }
        return !equal;
    })
    return removedPost;
}

function getObjectFields(obj) {
    let arr = [];
    Object.keys(obj).forEach(field => {
        const childField = obj[field];
        if (Array.isArray(childField) || (typeof childField === "string")) {
            arr.push([field]);
        } else {
            const pathsToFields = getObjectFields(childField);
            if (!Array.isArray(pathsToFields)) {
                pathsToFields = [pathsToFields];
            }
            pathsToFields.forEach(pathToField => {
                arr.push([field].concat(pathToField));
            })
            if (!pathsToFields.length) {
                arr.push([field]);
            }
        }
    })
    return arr;
}

function editPost(id, newPostFields) {
    const oldPost = getPost(id);
    const pathsToFieldsToChange = getObjectFields(newPostFields);
    pathsToFieldsToChange.forEach(pathToField => {
        let execstring = "";
        pathToField.forEach(field => {
            execstring += "['" + field + "']";
        })
        execstring = "oldPost" + execstring + " = " + "newPostFields" + execstring;
        // Not secure!
        eval(execstring);
    });
    posts[posts.findIndex(post => { return post.id == id })] = oldPost;
}

const config = [
    {
        fieldName: "user.name",
        fieldValue: ["@asenyarb", "@milky_way"]
    },
    {
        fieldName: "post.tags",
        fieldValue: ["#Interesting", "#tennis"]
    },
    {
        fieldName: "date",
        fieldValue: new Date("2020-02-26"),
        comparison: 'lower'
    }
];

const newPosts = getPosts(0, 10, config);

const config2 = [
    {
        fieldName: "post.likes",
        fieldValue: ["@asenyarb"]
    }
];

const newPosts2 = getPosts(0, 10, config2);

// const addInvalidPostResult = addPost({});

const addValidPostResult = addPost(
    {
        id: '45',
        user: {
            photo: "photos/comp.jpg",
            name: "@prog_mid"
        },
        date: new Date("2020-02-01T01:21"),
        post: {
            text: 'asdasd\
            ',
            photos: [],
            tags: ["#ASD"],
            likes: ["@asenyarb", "@milky_way"]
        },
    }
)

const postsWithAddedPost = getPosts();

editPost('45', { post: { likes: ["@asenyarb"], text: "asdasdadadadasdadas" } });

const postsWithEditedAddedPost = getPosts();

const editedNewPost = getPost(45);

const removedPost = removePost('45');

debugger;
