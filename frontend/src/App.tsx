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
import {PictureInsertDto} from "./model/PictureInsertDto.tsx";
import {ProtectedRoutes} from "./components/ProtectedRoutes.tsx";
import {AppUser} from "./model/AppUser.tsx";

function App() {

    const [pictures, setPictures] = useState<Picture[]>([])
    const [user, setUser] = useState<AppUser | null | undefined>(undefined)

    function loadPictures() {
        axios.get("/api/picture_get")
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

    function loadUser() {
        axios.get("api/auth/me")
            .then(response => {
                console.log(response.data)
                setUser(response.data)
            })
            .catch(errorResponse => {
                console.log(errorResponse)
                setUser(null)
            })
    }

    useEffect(() => {
        console.log("Mounting App!")
        loadPictures()
        loadUser()
    }, [])

    function insertPicture(pictureInsertDto: PictureInsertDto) {
        axios.post("/api/picture", pictureInsertDto)
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

    function deletePicture(id: string) {
        axios.delete("/api/picture/" + id)
            .then((response) => {
                console.log(response)
                loadPictures()
                alert("Foto wurde gelöscht.")
            })
            .catch((errorResponse) => {
                console.log(errorResponse)
                alert("Fehler beim Löschen des Fotos!")
            })
    }

    return (
        <div className="app">
            <Header user={user}/>
            <Routes>
                <Route path={"/"} element={<Home pictures={pictures}/>}/>
                <Route path={"/home"} element={<Home pictures={pictures}/>}/>
                <Route path={"/pictures"} element={<DisplayPictures pictures={pictures} user={user}/>}/>
                <Route path={"/picture/:id"} element={<DisplaySinglePicture/>}/>

                <Route element={<ProtectedRoutes user={user}/>}>
                    <Route path={"/add"} element={<AddPicture insertPicture={insertPicture}/>}/>
                    <Route path={"/update_picture/:id"} element={<UpdatePicture
                        updatePicture={updatePicture} deletePicture={deletePicture}/>}/>
                </Route>
            </Routes>
        </div>
    )
}

export default App
