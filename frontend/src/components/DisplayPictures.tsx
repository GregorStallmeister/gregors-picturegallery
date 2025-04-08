import {PictureCard} from "./PictureCard.tsx";
import {Picture} from "../model/Picture.tsx";
import {AppUser} from "../model/AppUser.tsx";
import {useState} from "react";

type Props = {
    pictures: Picture[]
    user: AppUser | null | undefined
}

export function DisplayPictures(props: Readonly<Props>) {

    const [favoritesOnly, setFavoritesOnly] = useState<boolean>(false)

    if (props === undefined || props === null) {
        return (
            <div>
                ... lade Bilder
            </div>
        )
    }

    if (props.user === null || props.user === undefined) {
        return (
            <>
                <div><input type="checkbox" disabled={true}/> zeige nur meine Favoriten (Login erforderlich)
                </div>
                <div className="display">
                    {
                        props.pictures.map(picture => (
                            <PictureCard key={picture.id} picture={picture} user={props.user}/>
                        ))
                    }
                </div>
            </>
        )
    }

    function mapPicture(picture: Picture) {
        if (! favoritesOnly) {
            return <PictureCard key={picture.id} picture={picture} user={props.user}/>
        }
        
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-expect-error
        if (props.user.favoritePicturesIds.indexOf(picture.id) > -1) {
            return <PictureCard key={picture.id} picture={picture} user={props.user}/>
        }
    }

    return (
        <>
            <div><input type="checkbox" onChange={event => {
                setFavoritesOnly(event.target.checked)
            }}/> zeige nur meine Favoriten
            </div>
            <div className="display">
                {
                    props.pictures.map(picture => (
                        mapPicture(picture)
                    ))
                }
            </div>
        </>
    )
}