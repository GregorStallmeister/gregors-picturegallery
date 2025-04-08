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
    const [appUser, setAppUser] = useState<AppUser | null | undefined>(undefined)

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
        axios.get("/api/auth/me")
            .then(response => {
                console.log("User loaded!")
                console.log("Response was: " + response.data)
                setAppUser(response.data)
            })
            .catch(errorResponse => {
                console.log("Error while loading user: " + errorResponse)
                setAppUser(null)
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

    function switchFavorite(id: string, boxChecked: boolean) {
        console.log(id + ", " + boxChecked)

        if (appUser === null || appUser === undefined) {
            console.log("Shall switch favorite, but no user logged in!")
            return
        }

        if (boxChecked) {
            appUser.favoritePicturesIds.push(id);
        } else {
            const index = appUser.favoritePicturesIds.indexOf(id)
            if (index > -1) {
                appUser.favoritePicturesIds.splice(index, 1)
            }
        }

        console.log(appUser.favoritePicturesIds)

        axios.put("/api/user/" + appUser.id, appUser)
            .then((response) => {
                    console.log(response)
                    loadUser()
                }
            )
            .catch((errorResponse) => {
                console.log(errorResponse)
            })
    }

    return (
        <div className="app">
            <Header appUser={appUser}/>
            <Routes>
                <Route path={"/"}
                       element={<Home pictures={pictures} appUser={appUser} switchFavorite={switchFavorite}/>}/>
                <Route path={"/home"}
                       element={<Home pictures={pictures} appUser={appUser} switchFavorite={switchFavorite}/>}/>
                <Route path={"/pictures"} element={<DisplayPictures pictures={pictures} user={appUser}/>}/>
                <Route path={"/picture/:id"}
                       element={<DisplaySinglePicture appUser={appUser} switchFavorite={switchFavorite}/>}/>

                <Route element={<ProtectedRoutes user={appUser}/>}>
                    <Route path={"/add"} element={<AddPicture insertPicture={insertPicture}/>}/>
                    <Route path={"/update_picture/:id"} element={<UpdatePicture
                        updatePicture={updatePicture} deletePicture={deletePicture}/>}/>
                </Route>
            </Routes>
        </div>
    )
}

export default App
