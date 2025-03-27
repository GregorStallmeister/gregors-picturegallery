import {ChangeEvent, useState} from "react";
import {Picture} from "../model/Picture.tsx";
import {useNavigate} from "react-router-dom";

type Props = {
    insertPicture(picture: Picture): void
}

export function AddPicture(props: Props) {

    const [picture, setPicture] = useState<Picture>({
        id: "id_dummy",
        imagePath: "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
        location: "",
        instant: "2025-03-26T09:17:30+01:00"
    })
    const navigate = useNavigate()

    function updatePicture(event: ChangeEvent<HTMLInputElement>) {
        const key: string = event.target.name
        const value: string = event.target.value
        setPicture({...picture, [key]: value})
    }

    return (
        <div>
            <h2>Foto hinzuf&uuml;gen</h2>
            <form onSubmit={(event => {
                event.preventDefault()
                props.insertPicture(picture)
                navigate("/pictures")
            })}>
                <input name="imagePath" placeholder={"Foto URL"} value={picture.imagePath} size={80}
                       onChange={event => updatePicture(event)}/>
                <br/>&nbsp;<br/>
                <input name="location" placeholder={"Aufnahmeort"} value={picture.location} size={80}
                       onChange={event => updatePicture(event)}/>
                <br/>&nbsp;<br/>
                <input name="instant" placeholder={"Datum und Uhrzeit"} value={picture.instant} size={80}
                       onChange={event => updatePicture(event)}/>
                <br/>&nbsp;<br/>
                <button>Speichern</button>
            </form>
        </div>
    )
}