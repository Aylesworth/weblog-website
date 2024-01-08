export class Post {
    constructor(
        public id: number,
        public title: string,
        public content: string,
        public author: string,
        public created: string,
        public updated: string,
        public comments: Comment[]
    ) {}
}
