window.posts.addAll([
    {
        id: '10',
        userPhoto: "photos/user-photo.jpg",
        userName: "@asenyarb",
        date: new Date("2020-03-01T14:45"),
        postText: 'Nabi Tajima was until recently the oldest person in the world. She was born in August 1900, \
            which made her the last person born in the 19th century. \
            She passed away in a hospital in southern Japan at the age of 117. \
            She had more than 160 descendants, including great-great-great grandchildren. \
            Mrs Tajima was the 3rd oldest person in recorded human history. \
            ',
        postPhotos: [],
        postTags: ["#interesting", "#Japan"],
        postLikes: ['@milky_way'],
    },
    {
        id: '9',
        userPhoto: "photos/planet.jpg",
        userName: "@milky_way",
        date: new Date("2020-02-29T11:00"),
        postText: 'Two Australian police officers were out in the ocean, and they were conducting safety and \
            compliance checks when a great white shark suddenly swam up to their small boat. \
            Luckily, the heart-stopping meeting ended quickly when the shark swam away.\
            ',
        postPhotos: [],
        postTags: ["#shark"],
        postLikes: ['@asenyarb']
    },
    {
        id: '8',
        userPhoto: "photos/comp.jpg",
        userName: "@prog_mid",
        date: new Date("2020-02-27T06:36"),
        postText: 'More than 71 people are dead, 17 missing, and more than 40,000 were displaced after a massive \
            storm struck the Philippines. One of the country’s officials said that the storm was not \
            strong enough to be rated as a typhoon, so many people were unprepared. \
            Dozens of them died in landslides and flooding caused by the heavy rains. \
            The country already suffered a number of deadly storms recently – Super \
            Typhoon Mangkhut killed more than 80 people in September and Typhoon Yutu killed 17 more.\
            ',
        postPhotos: [],
        postTags: ["#typhoon"],
        postLikes: ['@milky_way', '@asenyarb']
    },
    {
        id: '7',
        userPhoto: "photos/user-photo.jpg",
        userName: "@asenyarb",
        date: new Date("2020-02-25T17:30"),
        postText: 'In recent years, Australia’s south west state of Victoria saw a rapid increase of koalas, \
            meaning that the animals have been literally eating themselves out of house and home by \
            destroying gum trees. Their binging and booming population eventually caused them to starve. \
            Environmentalists have moved 500 koalas over the past year, and their objective now is to \
            control them and keep their numbers down. They gave the females contraceptive implants and then \
            released them into other forests.\
            ',
        postPhotos: [],
        postTags: ["#Australia", "#koalas"],
        postLikes: ['@prog_mid']
    },
    {
        id: '6',
        userPhoto: "photos/comp.jpg",
        userName: "@prog_mid",
        date: new Date("2020-02-20T05:56"),
        postText: 'One farmer from Kansas, USA decided to create cow art. He used his cattle, \
            a feed truck and drones to create a huge smiley face out of cows. \
            If that is not enough to make you smile, the farmer and artist is also known for \
            using his trombone to herd the cows, saying that they have happy cows in Kansas. \
            ',
        postPhotos: [],
        postTags: ["#cow", "#art"],
        postLikes: []
    },
    {
        id: '5',
        userPhoto: "photos/user-photo.jpg",
        userName: "@asenyarb",
        date: new Date("2020-02-15T07:30"),
        postText: 'Amazon is working on rules about technology which can recognize a person’s face. Amazon’s CEO, \
            Jeff Bezos, says that his team is working on these rules that he wants to present to Congress. \
            Bezos believes that facial recognition has many benefits, but there is also danger that it \
            can be abused.\
            ',
        postPhotos: [],
        postTags: ["#Amazon", "#facial_recognition", "#Interesting"],
        postLikes: []
    },
    {
        id: '4',
        userPhoto: "photos/planet.jpg",
        userName: "@milky_way",
        date: new Date("2020-02-10T21:21"),
        postText: 'We’ve all heard the saying ‘painting on a smile’. Well one Japanese artist has taken \
            that phrase to a whole new level. Nobumichi Asai has found a way to project and map \
            expressions onto people’s faces, effectively creating a facial mask. He and his team \
            displayed their three-dimensional facial projection mapping system to a select group of \
            spectators in Tokyo!\
            ',
        postPhotos: [],
        postTags: ["#Japan"],
        postLikes: ["@asenyarb"]
    },
    {
        id: '3',
        userPhoto: "photos/user-photo.jpg",
        userName: "@asenyarb",
        date: new Date("2020-02-05T05:30"),
        postText: 'At the 2018 Winter Olympics, the competition and the entertainment was heating up! Some \
            fire artists held a special show on a beach of a coastal city that is helping with the \
            Pyeongchang Games in South Korea. \
            Visitors braved the cold outside to watch the sparks fly in the show. The fire artists \
            came with other artists to celebrate the Olympics.\
            ',
        postPhotos: [],
        postTags: ["#South_Korea", "#Olympics", "#Interesting"],
        postLikes: []
    },
    {
        id: '2',
        userPhoto: "photos/planet.jpg",
        userName: "@milky_way",
        date: new Date("2020-02-07T14:00"),
        postText: 'Maria Sharapova is a tennis player. She comes from Russia. She wins an important match with \
            the best tennis player in the world. That player is Serena Williams. \
            Sharapova becomes very famous after this match. She is only 17 years old.\
            ',
        postPhotos: [],
        postTags: ["#tennis"],
        postLikes: []
    },
    {
        id: '1',
        userPhoto: "photos/comp.jpg",
        userName: "@prog_mid",
        date: new Date("2020-02-01T01:29"),
        postText: 'Malcolm X is a famous US civil rights leader. 55 years ago, someone shoots him in New \
            York City, USA. Malcolm X is born Malcolm Little in 1925. He changes his name after he \
            changes his religion to Islam.\
            ',
        postPhotos: [],
        postTags: ["#NY"],
        postLikes: ["@asenyarb", "@milky_way"]
    }
]);

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

console.log(`Posts with config `, config, ':', window.posts.getPage(0, 10, config));

const config2 = [
    {
        fieldName: "postLikes",
        fieldValue: ["@asenyarb"]
    }
];

console.log(`Posts with config `, config2, ':', window.posts.getPage(0, 10, config2));

console.log('Add invalid post result = ', window.posts.add({}));

console.log('Add valid post result = ', window.posts.add(
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
));

console.log('This added post:', window.posts.get('45'));

console.log('Posts with added post: ', window.posts.getPage(0, 11));

console.log("Editing post with id=45. New fields values: ", { postLikes: ["@asenyarb"], postText: "asdasdadadadasdadas" });
window.posts.edit('45', { postLikes: ["@asenyarb"], postText: "asdasdadadadasdadas" });

console.log('Posts with that post edited: ', window.posts.getPage(0, 11));

console.log('This edited post:', window.posts.get('45'));

console.log("Remove post with id=10. This post: ", window.posts.remove('10'));
