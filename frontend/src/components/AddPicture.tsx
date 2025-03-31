import {ChangeEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {PictureInsertDto} from "../model/PictureInsertDto.tsx";

type Props = {
    insertPicture(pictureInsertDto: PictureInsertDto): void
}

export function AddPicture(props: Props) {

    const [pictureInsertDto, setPictureInsertDto] = useState<PictureInsertDto>({
        imagePath: "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
        location: "",
        instant: "2025-03-26T09:17:30+01:00"
    })
    const navigate = useNavigate()

    function updatePicture(event: ChangeEvent<HTMLInputElement>) {
        const key: string = event.target.name
        const value: string = event.target.value
        setPictureInsertDto({...pictureInsertDto, [key]: value})
    }

    return (
        <div>
            <h2>Foto hinzuf&uuml;gen</h2>
            <form onSubmit={(event => {
                event.preventDefault()
                props.insertPicture(pictureInsertDto)
                navigate("/pictures")
            })}>
                <input name="imagePath" placeholder={"Foto URL"} value={pictureInsertDto.imagePath} size={80}
                       onChange={event => updatePicture(event)}/>
                <br/>&nbsp;<br/>
                <input name="location" placeholder={"Aufnahmeort"} value={pictureInsertDto.location} size={80}
                       onChange={event => updatePicture(event)}/>
                <br/>&nbsp;<br/>
                <input name="instant" placeholder={"Datum und Uhrzeit"} value={pictureInsertDto.instant} size={80}
                       onChange={event => updatePicture(event)}/>
                <br/>&nbsp;<br/>
                <button>Speichern</button>
            </form>
        </div>
    )
}