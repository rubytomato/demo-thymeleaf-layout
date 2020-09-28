package com.example.demo.ui;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Slf4j
public class Page {

  /**
   * * 全データ件数（変数）
   * * 1ページあたりの表示件数（定数 = 20）
   * * 全ページ数（変数） = 全データ件数 / 1ページあたりの表示件数（余りがあれば+1）
   * * ページボタン最大表示数（定数 = 5）
   * * 現在ページ番号（変数）
   * * 最初のページ番号（定数 = 1）
   * * 最後のページ番号（変数） = ページ数
   * * 前のページ位置（変数） = 現在ページ位置 - 1
   * * 次のページ位置（変数） = 現在ページ位置 + 1
   */

  private final int DEFAULT_PER_PAGE = 20;
  private final int DEFAULT_BUTTON_NUM = 5;

  // 全データ件数
  private int allDataNum;
  // 1ページ当たりの表示件数
  private int dataPerPage;
  // 全ページ数
  private int allPageNum;
  // ページボタン最大表示数
  private int pageButtonMaxNum;

  // 現在のページ番号
  private int currentPageNo;
  // 最初のページ番号
  private int firstPageNo;
  // 最後のページ番号
  private int lastPageNo;
  // 前のページ番号
  private int prevPageNo;
  // 次のページ番号
  private int nextPageNo;

  // ページ範囲
  private List<Integer> pageRange;

  public Page(int allDataNum, int dataPerPage, int currentPageNo) {
    this.allDataNum = allDataNum;
    this.dataPerPage = dataPerPage;
    calcAllPageNum();
    calcPageButtonMaxNum();

    this.currentPageNo = currentPageNo;
    this.firstPageNo = 1;
    this.lastPageNo = this.allPageNum;
    calcPrevPageNo();
    calcNextPageNo();

    calcPageRange();
  }

  // 全ページ数の計算
  private void calcAllPageNum() {
    allPageNum = allDataNum / dataPerPage;
    if (allDataNum % dataPerPage > 0) {
      allPageNum += 1;
    }
  }

  /*
   * 全ページ数 ＞ ページボタン最大表示数のとき = ページボタン最大表示数
   * 全ページ数 ≦ ページボタン最大表示数のとき = 全ページ数
   */
  private void calcPageButtonMaxNum() {
    if (allPageNum > DEFAULT_BUTTON_NUM) {
      pageButtonMaxNum = DEFAULT_BUTTON_NUM;
    } else {
      pageButtonMaxNum = allPageNum;
    }
  }

  private void calcPrevPageNo() {
    if ((currentPageNo - 1) < 1) {
      prevPageNo = 1;
    } else {
      prevPageNo = currentPageNo - 1;
    }
  }

  private void calcNextPageNo() {
    if ((currentPageNo + 1) > allPageNum) {
      nextPageNo = allPageNum;
    } else {
      nextPageNo = currentPageNo + 1;
    }
  }

  private void calcPageRange() {
    int rangeStart;
    int rangeEnd;
    if ((currentPageNo - 2) < firstPageNo) {
      rangeStart = firstPageNo;
      rangeEnd = rangeStart + pageButtonMaxNum - 1;
    } else if ((currentPageNo + 2) > lastPageNo) {
      rangeEnd = lastPageNo;
      rangeStart = rangeEnd - pageButtonMaxNum + 1;
    } else {
      rangeStart = currentPageNo - 2;
      rangeEnd = currentPageNo + 2;
    }

    pageRange = IntStream.range(rangeStart, rangeEnd + 1).boxed().collect(Collectors.toList());
  }

  // disabled
  public boolean disabledFirstPageNo() {
    return firstPageNo == currentPageNo;
  }

  public boolean disabledPrevPageNo() {
    return prevPageNo == currentPageNo;
  }

  public boolean disabledNextPageNo() {
    return nextPageNo == currentPageNo;
  }

  public boolean disabledLastPageNo() {
    return lastPageNo == currentPageNo;
  }

}
