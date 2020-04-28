const users = [
    {
        userName: "@asenyarb",
        userPhoto: "resources/img/asenya.png",
        password: "",
    },
    {
        userName: "@milkyway",
        userPhoto: "resources/img/planet.jpg",
        password: "",
    },
    {
        userPhoto: "resources/img/comp.jpg",
        userName: "@prog_mid",
        password: "",
    }
]

class Users{
    getUser(username){
        return users.find(user => user.userName === username);
    }

    login(username, password){
        const user = window.users.getUser(username);
        if (!user || user.password !== password){
            return null;
        } else {
            return user;
        }
    }
}

(() => {
    window.users = new Users();
})()