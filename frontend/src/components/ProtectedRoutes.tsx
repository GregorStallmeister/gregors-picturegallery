import {Navigate, Outlet} from "react-router-dom";
import {AppUser} from "../model/AppUser.tsx";

type Props = {
    user: AppUser | null | undefined
}

export function ProtectedRoutes(props: Props) {

    if (props.user === null || props.user === undefined) {
        return (
            <Navigate to="/"/>
        )
    }

    return <Outlet/>
}