import { Organization } from "../types/types";

export const defaultArticle = {
  id: 0,
  title: "",
  organizations: [] as Organization[],
  categoryName: "",
  contents: "",
  price: 0,
  createTime: "",
  tradeState: "",
  tags: [] as string[],
  photos: [] as string[],
  author: {
    id: 0,
    nickname: "",
    avatar: "",
    score: 0,
    validated: false,
    pushToken: "",
  },
  favoriteState: false,
  favoriteCount: 0,
  hit: 11,
  createdTime: "",
};
