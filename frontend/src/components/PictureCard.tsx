import {Picture} from "../model/Picture.tsx";
import {Link, useNavigate} from "react-router-dom";

type Props = {
    picture: Picture
}

export function PictureCard(props: Props) {
    const navigate = useNavigate()

    if (props === undefined || props.picture === undefined) {
        return (
            <div>
                ... lade Foto
            </div>
        )
    }

    return (
        <div className="card">
            <div className="cardEntry">
                <img src={props.picture.imagePath.replace(".jpg", "_205.jpg")}
                     alt={"Foto: " + props.picture.imagePath}/>
            </div>
            <div className="cardEntry">
                <Link to="/picture/{id}"
                      onClick={(event) => {
                          event.preventDefault()
                          navigate("/picture/" + props.picture.id)
                      }}>Detailansicht
                </Link>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <Link to="/picture/{id}"
                      onClick={(event) => {
                          event.preventDefault()
                          navigate("/update_picture/" + props.picture.id)
                      }}>Bearbeiten
                </Link>
            </div>
        </div>
    )
}