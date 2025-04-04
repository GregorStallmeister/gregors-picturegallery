import {AppUserRole} from "./AppUserRole.tsx";

export type AppUser = {
    id: string
    username: string
    role: AppUserRole
    favoritePicturesIds: string[]
}