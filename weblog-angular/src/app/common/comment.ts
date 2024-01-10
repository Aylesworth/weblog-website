import { User } from "./user";

export class Comment {
    public id?: string;
    public email?: string;
    public user?: User;
    public content?: string;
    public posted?: Date;
    public likes?: number;
    public liked?: boolean;
    public replies?: Comment[];
    public totalReplies?: number;
}
