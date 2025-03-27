import './App.css'
import {AddPicture} from "./components/AddPicture.tsx";
import {Picture} from "./model/Picture.tsx";
import axios from 'axios';
import {Header} from "./components/Header.tsx";
import {Route, Routes} from "react-router-dom";
import {Home} from "./components/Home.tsx";
import {DisplayPictures} from "./components/DisplayPictures.tsx";

function App() {

    function insertPicture(picture: Picture) {
        axios.post("/api/picture", picture)
            .then((response) => {
                console.log(response)
            })
            .catch((errorResponse) => {
                console.log(errorResponse)
            })
    }

    return (
        <div className="app">
            <Header/>
            <Routes>
                <Route path={"/"} element={<Home/>}/>
                <Route path={"/home"} element={<Home/>}/>
                <Route path={"/add"} element={<AddPicture insertPicture={insertPicture}/>}/>
                <Route path={"/pictures"} element={<DisplayPictures/>}/>
            </Routes>
        </div>
    )
}

export default App
