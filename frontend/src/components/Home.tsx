import {Picture} from "../model/Picture.tsx";
import {PictureCard} from "./PictureCard.tsx";

type Props = {
    pictures: Picture[]
}

export function Home(props: Props) {

    // SonarQube identifies the follwing code as a security hotspot:
    // const index: number = Math.floor(Math.random() * (props.pictures.length - 1))
    // The issue concerns a harmless activity:
    // random choosing a picture displayed on the welcome site (home) of my picture gallery.
    // I think, this is no risk.

    // New version: The unsecure Math.random() is replaced by a monster!
    // At this point, it was easier for me to create the monster than take lessons in using crypto.getRandomValues
    // or take lessons in correct syntax of regular expressions ;-)
    let randomString: string = crypto.randomUUID()
    console.log(randomString)
    while (randomString.indexOf("-") > -1) {
        randomString = randomString.replace("-", "")
    }
    while (randomString.indexOf("a") > -1) {
        randomString = randomString.replace("a", "")
    }
    while (randomString.indexOf("b") > -1) {
        randomString = randomString.replace("b", "")
    }
    while (randomString.indexOf("c") > -1) {
        randomString = randomString.replace("c", "")
    }
    while (randomString.indexOf("d") > -1) {
        randomString = randomString.replace("d", "")
    }
    while (randomString.indexOf("e") > -1) {
        randomString = randomString.replace("e", "")
    }
    while (randomString.indexOf("f") > -1) {
        randomString = randomString.replace("f", "")
    }
    console.log(randomString)
    randomString = randomString.substring(0, 6)

    let indexBase: number = Number(randomString)
    indexBase = indexBase / 1000000
    console.log(indexBase)

    const index: number = Math.floor(indexBase * (props.pictures.length - 1))

    return (
        <div className="display">
            <h2>Herzlich willkommen auf meiner Homepage mit einem Zufallsfoto!</h2>
            <PictureCard picture={props.pictures[index]} large={true}/>
        </div>
    )
}