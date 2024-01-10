export class Comment {
    public id?: string;
    public email?: string;
    public content?: string;
    public posted?: Date;
    public likes?: number;
    public replies?: Comment[];
}
