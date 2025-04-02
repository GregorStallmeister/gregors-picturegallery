import {Picture} from "../model/Picture.tsx";

type Props = {
    picture: Picture
}

export function PictureDetailed(props: Props) {

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
                <img src={props.picture.imagePath.replace(".jpg", "_800.jpg")}
                     alt={"Foto: " + props.picture.imagePath}/>
                <br/>
            </div>
            <div className="cardEntry">
                Aufnameort: {props.picture.location.toString()}
                <br/>
                Datum und Uhrzeit: {props.picture.instant.toString()}
            </div>
        </div>
    )
}