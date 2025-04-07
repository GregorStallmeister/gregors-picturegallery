import {Picture} from "../model/Picture.tsx";
import {AppUser} from "../model/AppUser.tsx";
import {useEffect, useState} from "react";

type Props = {
    picture: Picture
    appUser: AppUser | null | undefined
    switchFavorite(id: string, boxChecked: boolean): void
}

export function PictureDetailed(props: Props) {
    const [boxChecked, setBoxChecked] = useState<boolean>(false)

    useEffect(() => {
        if (props.appUser !== null && props.appUser !== undefined && props.appUser.favoritePicturesIds.indexOf(props.picture.id) > -1) {
            setBoxChecked(true)
        }
    }, []);

    if (props.picture === undefined) {
        return (
            <div>
                ... (noch) kein Foto vorhanden
            </div>
        )
    }

    if (props.appUser === null || props.appUser === undefined) {
        return (
            <div className="card">
                <div className="cardEntry">
                    <img src={props.picture.imagePath.replace(".jpg", "_800.jpg")}
                         alt={"Foto: " + props.picture.imagePath}/>
                    <br/>
                </div>
                <div className="cardEntry">
                    {props.picture.location.toString()}, {props.picture.instant.toString()}
                    &nbsp;&nbsp;
                    <input type="checkbox" id="favorite" value="favorite" disabled={true}/>
                    <label>Einloggen, um dieses Foto als Favorit festlegen oder abwählen zu können</label>
                </div>
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
                {props.picture.location.toString()}, {props.picture.instant.toString()}
                &nbsp;&nbsp;
                <input type="checkbox" id="favorite" value="favorite" checked={boxChecked} onChange={
                    event => {
                        setBoxChecked(event.target.checked)
                    }
                }/><label>Favorit</label>
            </div>
        </div>
    )
}