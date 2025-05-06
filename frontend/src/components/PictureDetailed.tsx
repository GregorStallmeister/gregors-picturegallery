import {Picture} from "../model/Picture.tsx";
import {AppUser} from "../model/AppUser.tsx";
import {useEffect, useState} from "react";
import {WeatherResponse} from "../model/WeatherResponse.tsx";
import axios from "axios";

type Props = {
    picture: Picture
    appUser: AppUser | null | undefined
    switchFavorite(id: string, boxChecked: boolean): void
}

export function PictureDetailed({appUser, picture, switchFavorite}: Readonly<Props>) {
    const [boxFavoriteChecked, setBoxFavoriteChecked] = useState<boolean>(false)
    const [weatherResponse, setWeatherResponse] = useState<WeatherResponse | null | undefined>()

    function loadWeather() {
        axios.get("/api/weather/" + picture.positionInGrid)
            .then((response) => {
                setWeatherResponse(response.data)
                console.log("Weather loaded!")
            })
            .catch((error) => {
                setWeatherResponse(null)
                if (axios.isAxiosError(error)) {
                    console.log("Loading weather failed. The backend server returned status code: " + error.status +
                        ", the complete response you can see in the following entry:")
                    console.log(error.response)
                }
                else {
                    console.log("Error while loading weather: " + error)
                }
            })
    }

    useEffect(() => {
        if (picture !== undefined && picture !== null) {
            loadWeather()
        }

        if (appUser !== null && appUser !== undefined
            && picture !== null && picture !== undefined
            && appUser.favoritePicturesIds !== null
            && appUser.favoritePicturesIds !== undefined
            && appUser.favoritePicturesIds.indexOf(picture.id) > -1) {
            setBoxFavoriteChecked(true)
        }
        else {
            setBoxFavoriteChecked(false)
        }
    }, [appUser, picture]);

    if (picture === undefined) {
        return (
            <div>
                ... (noch) kein Foto vorhanden
            </div>
        )
    }

    function createWindDirection(windDirection: number): string {
        if (windDirection <= 30) {
            return "Nord"
        }
        if (windDirection <= 60) {
            return "Nord-Ost"
        }
        if (windDirection <= 120) {
            return "Ost"
        }
        if (windDirection <= 150) {
            return "Süd-Ost"
        }
        if (windDirection <= 210) {
            return "Süd"
        }
        if (windDirection <= 240) {
            return "Süd-West"
        }
        if (windDirection <= 300) {
            return "West"
        }
        if (windDirection <= 330) {
            return "Nord-West"
        }
        if (windDirection <= 360) {
            return "Nord"
        }

        return windDirection + " Grad"
    }

    function createWeatherString(): string {
        let weatherString = "Wetter aktuell: "

        if (weatherResponse !== null && weatherResponse !== undefined) {
            weatherString += weatherResponse.temperature.replace(".", ",")
            weatherString += ", gefühlt " + weatherResponse.tempApparent.replace(".", ",")
            weatherString += " - bewölkt zu " + weatherResponse.cloudCover
            weatherString += " - Niederschlag " + weatherResponse.precipitation.replace(".", ",")
            weatherString += " - Wind " + weatherResponse.windSpeed.replace(".", ",")
            weatherString += " aus " + createWindDirection(weatherResponse.windDirection)
            weatherString += ", Böen " + weatherResponse.windGusts.replace(".", ",")
            weatherString += " - relative Luftfeuchtigkeit " + weatherResponse.relativeHumidity
            weatherString += " - Luftdruck " + weatherResponse.pressureMsl.replace(".", ",")
            weatherString += " bezogen auf Meereshöhe, tatsächlich " + weatherResponse.surfacePressure.replace(".", ",")
            weatherString += " (Quelle: Open Meteo API)"
        }
        else {
            weatherString += "leider nicht verfügbar"
        }

        return weatherString
    }

    function createDatTimeString(): string {
        return picture.instant.replace("T", ", ").replace("Z", " (GMT)")
    }

    if (appUser === null || appUser === undefined) {
        return (
            <div className="card">
                <div className="cardEntry">
                    <img src={picture.imagePath.replace(".jpg", "_800.jpg")}
                         alt={"Foto: " + picture.imagePath}/>
                    <br/>
                </div>
                <div className="cardEntry">
                    {picture.location.toString()}, {createDatTimeString()}
                    &nbsp;&nbsp;
                    <label>
                        <input type='checkbox' id='favorite' value='favorite' disabled={true}/>Einloggen,&nbsp;um&nbsp;dieses&nbsp;Foto&nbsp;als&nbsp;Favorit&nbsp;festlegen&nbsp;oder&nbsp;abwählen&nbsp;zu&nbsp;können
                    </label>
                </div>
                <div className="cardEntry">
                    {createWeatherString()}
                </div>
            </div>
        )
    }

    return (
        <div className="card">
            <div className="cardEntry">
                <img src={picture.imagePath.replace(".jpg", "_800.jpg")}
                     alt={"Foto: " + picture.imagePath}/>
                <br/>
            </div>
            <div className="cardEntry">
                {picture.location.toString()}, {createDatTimeString()}
                &nbsp;&nbsp;
                <label><input type="checkbox" id="favorite" value="favorite" checked={boxFavoriteChecked} onChange={
                    event => {
                        setBoxFavoriteChecked(event.target.checked)
                        console.log(event.target.checked)
                        switchFavorite(picture.id, event.target.checked)
                    }
                }/>Favorit</label>
            </div>
            <div className="cardEntry">
                {createWeatherString()}
            </div>
        </div>
    )
}