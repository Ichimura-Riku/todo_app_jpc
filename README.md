# todoAppJpc

## 導入メモ

- material 3
- dynamic color
- jetpack compose
- Hilt
- Lint
  - Hiltやろうとしてアノテーションを入れるところ意識しながら開発してたけどどこに入れれば良いかわかんなかった！こういう時に便利だよってGPTくんが教えてくれました！
- spotless
- room

## 技術選定

kaptとkspではkspの方が早いらしいのでこれを使います

## ルール

### コミット順序

- 実行してビルドが通るかを確認、動作が不安定かどうかもチェック
- する時は基本、spotlessにかけてフォーマットチェック
- 機能単位でこまめにコミット

### コメントアウト

rmvはあとで消すコメントアウトで、コミット時に確認するようにする

# 実装ログ

[devLog.md](/devLog.md)