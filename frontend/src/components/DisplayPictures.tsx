import {PictureCard} from "./PictureCard.tsx";
import {Picture} from "../model/Picture.tsx";
import {AppUser} from "../model/AppUser.tsx";

type Props = {
    pictures: Picture[]
    user: AppUser | null | undefined
}

export function DisplayPictures(props: Props) {

    if (props === undefined || props === null) {
        return (
            <div>
                ... lade Bilder
            </div>
        )
    }

    return (
        <div className="display">
            {
                props.pictures.map(picture => (
                    <PictureCard  key={picture.id} picture={picture} user={props.user}/>
                ))
            }
        </div>
    )
}