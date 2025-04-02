import {Navigate, Outlet} from "react-router-dom";

type Props = {
    user: string | null | undefined
}

export function ProtectedRoutes(props: Props) {

    if (props.user === null || props.user === undefined) {
        return (
            <Navigate to="/"/>
        )
    }

    return <Outlet/>
}