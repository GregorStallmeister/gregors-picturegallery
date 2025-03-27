import {Picture} from "../model/Picture.tsx";
import {PictureCard} from "./PictureCard.tsx";

type Props = {
    pictures: Picture[]
}

export function Home(props: Props) {
    return (
        <div className="display">
            <PictureCard picture={props.pictures[4]} large={true}/>
        </div>
    )
}