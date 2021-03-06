import { atom } from "recoil/dist";

export const joinNicknameState = atom({
  key: "joinNicknameState",
  default: "",
});

export const joinAvatarState = atom({
  key: "joinAvatarState",
  default: "",
});

export const joinSubmitState = atom({
  key: "joinSubmitState",
  default: false,
});

export const joinNicknameDuplicatedState = atom({
  key: "duplicateState",
  default: false,
});

export const joinModalState = atom({
  key: "joinModalState",
  default: false,
});
