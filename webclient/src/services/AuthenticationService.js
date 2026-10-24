import ApiService from "./ApiService";
import urlService from "./UrlService";

const Role = Object.freeze({ CUSTOMER: "customer", ADMIN: "admin" });

const isAnyUserLoggedIn = () => localStorage.getItem("user") !== null;

const isUserLoggedIn = (username) => {
    if (!isAnyUserLoggedIn())
        return false;

    return JSON.parse(localStorage.getItem("user")).username === username;
}

const getCurrentUser = () => structuredClone(JSON.parse(localStorage.getItem("user")));

const login = async (username, password) => {
    const { body } = await ApiService.signIn(username, password);

    if (body !== null && body.status === ApiService.statusCode.OK) {
        localStorage.setItem("user", JSON.stringify({username: username}));
        localStorage.setItem("token", body.data.sessionId);
    }

    return body;
};

const handleGoogleLoginRedirect = (username, token, navigate) => {
    if (token && username) {
        localStorage.setItem("user", JSON.stringify({ username: username }));
        localStorage.setItem("token", token);
        return true;
    }
    return false;
};


const isLoggedInUserAdmin = () => {
    if (!isAnyUserLoggedIn())
        return false;

    return JSON.parse(localStorage.getItem("user")).role === Role.ADMIN;
}

const logout = async () => {
    const { body } = await ApiService.signOut();
    if (body)
        localStorage.removeItem("user");

    return body;
};

const getToken = () => {
    const token = localStorage.getItem("token");
    return token ? `Bearer ${token}` : null;
}


const AuthenticationService = Object.freeze({
    isAnyUserLoggedIn,
    isUserLoggedIn,
    getCurrentUser,
    isLoggedInUserAdmin,
    login,
    logout,
    getToken,
    handleGoogleLoginRedirect,
});

export default AuthenticationService;
