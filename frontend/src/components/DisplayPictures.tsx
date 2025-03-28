import {PictureCard} from "./PictureCard.tsx";
import {Picture} from "../model/Picture.tsx";

type Props = {
    pictures: Picture[]
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
                    <PictureCard  key={picture.id} picture={picture} large={false}/>
                ))
            }
        </div>
    )
}