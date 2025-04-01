import {Link} from "react-router-dom";

export function Header() {

    function login() {
        alert("Login")

        const host
            = window.location.host === 'localhost:5173'
            ? 'http://localhost:8080'
            : window.location.origin

        window.open(host + '/oauth2/authorization/github', '_self')
    }

    return (
        <div className="header">
            Gregors Fotogalerie
            &nbsp;&nbsp;+++&nbsp;&nbsp;
            <Link to="/home">Home</Link>
            &nbsp;&nbsp;+++&nbsp;&nbsp;
            <Link to="/pictures">Fotos ansehen</Link>
            &nbsp;&nbsp;+++&nbsp;&nbsp;
            <Link to="/add">Foto hinzuf&uuml;gen</Link>
            &nbsp;&nbsp;+++&nbsp;&nbsp;
            <button onClick={login}>Login</button>
            <hr/>
        </div>
    )
}