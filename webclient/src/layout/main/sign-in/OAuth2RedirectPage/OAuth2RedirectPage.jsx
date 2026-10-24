import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthenticationService from "services/AuthenticationService";
import UrlService from "../../../../services/UrlService";

const params = new URLSearchParams(window.location.search);
const token = params.get("token");
const username = params.get("user");

export default function OAuth2RedirectPage() {
    const navigate = useNavigate();

    useEffect(() => {
        if (AuthenticationService.handleGoogleLoginRedirect(username, token))
            navigate(UrlService.urls.home);
        else
            navigate("");
    }, []);

    return <div>Completing Google login...</div>;
}
