import {Picture} from "../model/Picture.tsx";
import {PictureCard} from "./PictureCard.tsx";

type Props = {
    pictures: Picture[]
}

export function Home(props: Props) {

    // SonarQube identifies the follwing code as a security hotspot:
    const index: number = Math.floor(Math.random() * (props.pictures.length - 1))
    // The issue concerns a harmless activity:
    // random choosing a picture displayed on the welcome site (home) of my picture gallery.
    // I think, this is no risk.

    return (
        <div className="display">
            <h2>Herzlich willkommen auf meiner Homepage mit einem Zufallsfoto!</h2>
            <PictureCard picture={props.pictures[index]} large={true}/>
        </div>
    )
}