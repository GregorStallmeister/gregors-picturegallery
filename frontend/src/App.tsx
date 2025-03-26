import './App.css'
import {AddPicture} from "./components/AddPicture.tsx";
import {Picture} from "./model/Picture.tsx";
import axios from 'axios';

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
            <AddPicture insertPicture={insertPicture}/>
        </div>
    )
}

export default App
