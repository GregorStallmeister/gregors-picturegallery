import {Picture} from "../model/Picture.tsx";
import {AppUser} from "../model/AppUser.tsx";
import {useEffect, useState} from "react";

type Props = {
    picture: Picture
    appUser: AppUser | null | undefined
    switchFavorite(id: string, boxChecked: boolean): void
}

export function PictureDetailed( props: Readonly<Props>) {
    const [boxFavoriteChecked, setBoxFavoriteChecked] = useState<boolean>(false)

    useEffect(() => {
        if (props.appUser !== null && props.appUser !== undefined
            && props.picture !== null && props.picture !== undefined
            && props.appUser.favoritePicturesIds !== null
            && props.appUser.favoritePicturesIds !== undefined
            && props.appUser.favoritePicturesIds.indexOf(props.picture.id) > -1) {
            setBoxFavoriteChecked(true)
        }
        else {
            setBoxFavoriteChecked(false)
        }
    }, [props.appUser, props.picture]);

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
                    <label>
                        <input type='checkbox' id='favorite' value='favorite' disabled={true}/>Einloggen,&nbsp;um&nbsp;dieses&nbsp;Foto&nbsp;als&nbsp;Favorit&nbsp;festlegen&nbsp;oder&nbsp;abwählen&nbsp;zu&nbsp;können
                    </label>
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
                <label><input type="checkbox" id="favorite" value="favorite" checked={boxFavoriteChecked} onChange={
                    event => {
                        setBoxFavoriteChecked(event.target.checked)
                        console.log(event.target.checked)
                        props.switchFavorite(props.picture.id, event.target.checked)
                    }
                }/>Favorit</label>
            </div>
        </div>
    )
}