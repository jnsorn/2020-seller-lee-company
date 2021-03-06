import React from "react";
import { FlatList, View } from "react-native";
import OrganizationItem from "./OrganizationItem";
import { useRecoilValue } from "recoil/dist";
import { organizationListState } from "../../states/organizationState";

interface GroupListProps {
  isGroupFiltering: boolean;
  isFeed: boolean;
}

export default function OrganizationList({
  isGroupFiltering,
  isFeed,
}: GroupListProps) {
  const myGroupList = useRecoilValue(organizationListState);
  const groupListInFilter = [
    {
      id: 0,
      name: "전체",
      code: "000000",
    },
  ].concat(myGroupList);
  return (
    <View>
      <FlatList
        data={isGroupFiltering ? groupListInFilter : myGroupList}
        renderItem={({ item }) => (
          <OrganizationItem
            isGroupFiltering={isGroupFiltering}
            isFeed={isFeed}
            organization={item}
          />
        )}
        keyExtractor={(item, index) => `${index}`}
      />
    </View>
  );
}
