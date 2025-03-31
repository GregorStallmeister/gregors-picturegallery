import './App.css'
import {AddPicture} from "./components/AddPicture.tsx";
import {Picture} from "./model/Picture.tsx";
import axios from 'axios';
import {Header} from "./components/Header.tsx";
import {Route, Routes} from "react-router-dom";
import {Home} from "./components/Home.tsx";
import {DisplayPictures} from "./components/DisplayPictures.tsx";
import {useEffect, useState} from "react";
import {DisplaySinglePicture} from "./components/DisplaySinglePicture.tsx";
import {UpdatePicture} from "./components/UpdatePicture.tsx";

function App() {

    const [pictures, setPictures] = useState<Picture[]>([])

    function loadPictures() {
        axios.get("/api/picture")
            .then((response) => {
                console.log("Pictures loaded!")
                console.log(response)
                setPictures(response.data)
            })
            .catch((errorResponse) => {
                console.log("Error while attempting to load pictures!")
                console.log(errorResponse)
            })
    }

    useEffect(() => {
        console.log("Mounting App!")
        loadPictures()
    }, [])

    function insertPicture(picture: Picture) {
        axios.post("/api/picture", picture)
            .then((response) => {
                console.log(response)
                loadPictures()
                alert("Foto wurde erfolgreich hinzugefügt.")
            })
            .catch((errorResponse) => {
                console.log(errorResponse)
                alert("Fehler beim Hinzufügen des Fotos!")
            })
    }

    function updatePicture(picture: Picture) {
        axios.put("/api/picture/" + picture.id, picture)
            .then((response) => {
                console.log(response)
                loadPictures()
                alert("Foto wurde erfolgreich aktualisiert.")
            })
            .catch((errorResponse) => {
                console.log(errorResponse)
                alert("Fehler beim Aktualisieren des Fotos!")
            })
    }

    return (
        <div className="app">
            <Header/>
            <Routes>
                <Route path={"/"} element={<Home pictures={pictures}/>}/>
                <Route path={"/home"} element={<Home pictures={pictures}/>}/>
                <Route path={"/add"} element={<AddPicture insertPicture={insertPicture}/>}/>
                <Route path={"/pictures"} element={<DisplayPictures pictures={pictures}/>}/>
                <Route path={"/picture/:id"} element={<DisplaySinglePicture/>}/>
                <Route path={"/update_picture/:id"} element={<UpdatePicture updatePicture={updatePicture}/>}/>
            </Routes>
        </div>
    )
}

export default App
