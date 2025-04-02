import {useLocation} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
import {Picture} from "../model/Picture.tsx";
import {PictureDetailed} from "./PictureDetailed.tsx";

export function DisplaySinglePicture() {
    const location = useLocation()
    console.log("location.pathname: " + location.pathname)
    const id: string = location.pathname.substring(location.pathname.lastIndexOf("/") + 1)
    console.log("id: " + id)
    const [picture, setPicture] = useState<Picture>()

    useEffect(() => {
        axios.get("/api/picture_get/" + id)
            .then((response) => {
                setPicture(response.data)
                console.log("Single Picture loaded!")
            })
            .catch((errorResponse) => {
                console.log("Error while loading single picture: " + errorResponse)
            })
    }, [id])

    if (picture !== undefined && picture !== null)
    {
        return (
            <div>
                <PictureDetailed picture={picture}/>
            </div>
        )
    }
    else {
        return (
            <div>Foto wurde nicht gefunden!</div>
        )
    }
}