import { User } from "./user";

export class Post {        
    public id?: string;
    public title?: string;
    public content?: string;
    public author?: string;
    public authorDetails?: User;
    public thumbnail?: string;
    public created?: string;
    public updated?: string;
}
