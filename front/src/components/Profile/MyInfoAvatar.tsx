import React, { useEffect } from "react";
import {
  Alert,
  Image,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import colors from "../../colors";
import { useRecoilState, useRecoilValue } from "recoil/dist";
import {
  launchImageLibraryAsync,
  MediaTypeOptions,
  PermissionStatus,
} from "expo-image-picker";
import moment from "moment";
// @ts-ignore
import { RNS3 } from "react-native-aws3";
import { s3Secret } from "../../secret";
import * as Permissions from "expo-permissions";
import { myInfoAvatarState } from "../../states/myInfoState";
import {
  memberAvatarState,
  memberNicknameState,
} from "../../states/memberState";

export default function MyInfoAvatar() {
  const memberNickname = useRecoilValue(memberNicknameState);
  const [myInfoAvatar, setMyInfoAvatar] = useRecoilState(myInfoAvatarState);
  const memberAvatar = useRecoilValue(memberAvatarState);

  useEffect(() => {
    requestPermission();
    setMyInfoAvatar(memberAvatar);
  }, [memberAvatar]);

  const requestPermission = async () => {
    const { status } = await Permissions.askAsync(Permissions.CAMERA_ROLL);
    if (status !== PermissionStatus.GRANTED) {
      Alert.alert("사진 업로드 위해 갤러리 접근 권한이 필요합니다.");
    }
  };

  const pickPhoto = async () => {
    const result = await launchImageLibraryAsync({
      mediaTypes: MediaTypeOptions.All,
      allowsEditing: true,
      quality: 0.3,
    });
    if (!result.cancelled) {
      const date = new Date();
      const format = "YYYY.MM.DD_HH:mm:ss";
      const now = moment(date).format(format);
      const file = {
        uri: result.uri,
        name: memberNickname + "_" + now + ".jpeg",
        type: "image/jpeg",
      };

      const options = {
        keyPrefix: "images/",
        bucket: "seller-lee",
        region: "ap-northeast-2",
        accessKey: s3Secret.accessKey,
        secretKey: s3Secret.secretKey,
        successActionStatus: 201,
      };

      const response = await RNS3.put(file, options);
      setMyInfoAvatar(response.body.postResponse.location);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.imageContainer}>
        <Image
          style={styles.avatar}
          source={{ uri: myInfoAvatar ? myInfoAvatar : undefined }}
          defaultSource={require("../../../assets/user.png")}
        />
      </View>
      <TouchableOpacity style={styles.button} onPress={pickPhoto}>
        <Text style={styles.text}>사진 변경</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    aspectRatio: 2,
    alignItems: "center",
    justifyContent: "center",
    paddingVertical: 10,
  },
  imageContainer: {
    aspectRatio: 1,
    width: "70%",
    height: "70%",
  },
  avatar: {
    aspectRatio: 1,
    resizeMode: "cover",
    borderRadius: 100,
  },
  button: {
    marginTop: 10,
  },
  text: {
    color: colors.primary,
  },
});
