const users = [
    {
        userName: "@asenyarb",
        userPhoto: "resources/img/asenya.png"
    },
    {
        userName: "@milkyway",
        userPhoto: "resources/img/planet.jpg"
    },
    {
        userPhoto: "resources/img/comp.jpg",
        userName: "@prog_mid",
    }
]

class Users{
    getUser(username){
        return users.find(user => user.userName === username);
    }
}

(() => {
    window.users = new Users();
})()