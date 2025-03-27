import {Picture} from "../model/Picture.tsx";

type Props = {
    picture: Picture
    large: boolean
}

export function PictureCard(props: Props) {
    if (props === undefined || props.picture === undefined) {
        return (
            <div>
                ... lade Foto
            </div>
        )
    }

    if (props.large) {
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
    } else {
        return (
            <div className="card">
                <div className="cardEntry">
                <img src={props.picture.imagePath.replace(".jpg", "_205.jpg")}
                     alt={"Foto: " + props.picture.imagePath}/>
                </div>
            </div>
        )
    }
}