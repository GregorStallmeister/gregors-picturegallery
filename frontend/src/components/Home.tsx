import {Picture} from "../model/Picture.tsx";
import {PictureCard} from "./PictureCard.tsx";

type Props = {
    pictures: Picture[]
}

export function Home(props: Props) {

    const index: number = Math.floor(Math.random() * (props.pictures.length - 1))

    return (
        <div className="display">
            <h2>Herzlich willkommen auf meiner Homepage mit einem Zufallsfoto!</h2>
            <PictureCard picture={props.pictures[index]} large={true}/>
        </div>
    )
}