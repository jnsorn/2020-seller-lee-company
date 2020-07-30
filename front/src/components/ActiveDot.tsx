/**
 * @author joseph415
 */

import React from "react";
import { StyleSheet, View } from "react-native";

export default function ActiveDot() {
  return <View style={styles.activeDot} />;
}

const styles = StyleSheet.create({
  activeDot: {
    backgroundColor: "#eeecda",
    width: 8,
    height: 8,
    borderRadius: 4,
    marginLeft: 3,
    marginRight: 3,
    marginTop: 3,
    marginBottom: 3,
  },
});