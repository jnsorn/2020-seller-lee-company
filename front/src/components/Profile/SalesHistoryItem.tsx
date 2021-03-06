import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import theme from "../../colors";
import { ArticleCardProps } from "../../types/types";
import ArticleCard from "../Common/ArticleCommon/ArticleCard";
import { articlesAPI } from "../../api/api";
import { useRecoilState } from "recoil/dist";
import { articleSalesHistoryState } from "../../states/articleState";

interface OnSaleHistoryListProps {
  isCompletedTab: boolean;
  article: ArticleCardProps;
}

export default function SalesHistoryItem({
  isCompletedTab,
  article: {
    id,
    title,
    price,
    thumbnail,
    tradeState,
    favoriteState,
    favoriteCount,
    createdTime,
  },
}: OnSaleHistoryListProps) {
  const [articles, setArticles] = useRecoilState(articleSalesHistoryState);

  const onClickTradeState = async (tradeStateProp: string) => {
    await articlesAPI.putByTradeState(id, { tradeState: tradeStateProp });
    if (tradeStateProp === "COMPLETED") {
      setArticles(articles.filter((article) => article.id !== id));
    }
  };

  const onClickTradeStateByCompleted = async (tradeStateProp: string) => {
    await articlesAPI.putByTradeState(id, { tradeState: tradeStateProp });
    if (tradeStateProp === "ON_SALE") {
      setArticles(articles.filter((article) => article.id !== id));
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.articleContainer}>
        <ArticleCard
          id={id}
          title={title}
          price={price}
          createdTime={createdTime}
          tradeState={tradeState}
          favoriteState={favoriteState}
          favoriteCount={favoriteCount}
          thumbnail={thumbnail}
        />
      </View>
      {isCompletedTab ? (
        <TouchableOpacity
          style={styles.buttonContainer}
          activeOpacity={0.5}
          onPress={() => onClickTradeStateByCompleted("ON_SALE")}
        >
          <Text style={styles.buttonText}>판매중</Text>
        </TouchableOpacity>
      ) : (
        <View style={styles.halfContainer}>
          <TouchableOpacity
            style={styles.buttonHalfContainer}
            activeOpacity={0.5}
            onPress={() =>
              onClickTradeState(
                tradeState === "판매중" ? "RESERVED" : "ON_SALE",
              )
            }
          >
            <Text style={styles.buttonText}>
              {tradeState === "예약중" ? "판매중" : "예약중"}
            </Text>
          </TouchableOpacity>
          <View style={styles.divider} />
          <TouchableOpacity
            style={styles.buttonHalfContainer}
            activeOpacity={0.5}
            onPress={() => onClickTradeState("COMPLETED")}
          >
            <Text style={styles.buttonText}>판매 완료</Text>
          </TouchableOpacity>
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  articleContainer: {
    marginTop: 5,
    marginBottom: 10,
  },
  buttonContainer: {
    aspectRatio: 9,
    alignItems: "center",
    justifyContent: "center",
    borderRadius: 10,
    borderWidth: 1,
    borderColor: theme.secondary,
  },
  halfContainer: {
    aspectRatio: 9,
    flexDirection: "row",
    borderRadius: 10,
    borderWidth: 1,
    borderColor: theme.secondary,
  },
  buttonHalfContainer: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  buttonText: {
    fontSize: 14,
    color: theme.primary,
  },
  divider: {
    borderColor: theme.border,
    borderWidth: 0.5,
    marginVertical: 3,
  },
});
