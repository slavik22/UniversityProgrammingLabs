export interface BaseModel {
    id?: number;
    creationTimestamp?: Date;
    creationUser?: string;
    updateTimestamp?: Date;
    updateUser?: string;
    version?: string;
}