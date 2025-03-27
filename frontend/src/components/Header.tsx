import {Link} from "react-router-dom";

export function Header() {
    return (
        <div className="header">
            Gregors Fotogalerie
            &nbsp;&nbsp;+++&nbsp;&nbsp;
            <Link to="/home">Home</Link>
            &nbsp;&nbsp;+++&nbsp;&nbsp;
            <Link to="/pictures">Fotos ansehen</Link>
            &nbsp;&nbsp;+++&nbsp;&nbsp;
            <Link to="/add">Foto hinzuf&uuml;gen</Link>
            <hr/>
        </div>
    )
}