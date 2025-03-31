import {ChangeEvent, useEffect, useState} from "react";
import {Picture} from "../model/Picture.tsx";
import {useLocation, useNavigate} from "react-router-dom";
import axios from "axios";

type Props = {
    updatePicture(picture: Picture): void
}

export function UpdatePicture(props: Props) {
    const location = useLocation()
    const id: string = location.pathname.substring(location.pathname.lastIndexOf("/") + 1)

    const [picture, setPicture] = useState<Picture>()
    const navigate = useNavigate()

    useEffect(() => {
        axios.get("/api/picture/" + id)
            .then((response) => {
                setPicture(response.data)
                console.log("Picture to update loaded!")
            })
            .catch((errorResponse) => {
                console.log("Error while loading picture to update: " + errorResponse)
            })
    }, [id])

    function updatePicture(event: ChangeEvent<HTMLInputElement>) {
        const key: string = event.target.name
        const value: string = event.target.value
        if (picture !== undefined)
            setPicture({...picture, [key]: value})
    }

    if (picture !== undefined && picture !== null) {
        return (
            <div>
                <h2>Foto bearbeiten</h2>
                <form onSubmit={(event => {
                    event.preventDefault()
                    props.updatePicture(picture)
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
    } else {
        return (
            <div>
                Foto wurde nicht gefunden mit der ID {id}!
            </div>
        )
    }
}