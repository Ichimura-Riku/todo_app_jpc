# todoAppJpc

# 実装ログ

[devLog.md](/devLog.md)

# 開発目的

- JetpackComposeでの実装に挑戦したかった
- インターンでうまく実装できなかった
- 未知の技術に挑戦
- アーキテクチャを意識した設計がしたい
- UIもある程度凝った実装ができるようになりたい

# Todoアプリである理由

- バックエンドの処理(Room)を使う
- デフォルトのTodoアプリがUI設計のお手本として素晴らしい
- それでも多少は改良点が見つかった
	- デフォルトTodoアプリがシンプル過ぎると感じた
		- スマホ初心者には難しいかもしれない
	- さらに、全体一覧表示がない
	- ワンアクションで完了にしたい
	- gmailみたいに、スナックバーで完了を元に戻したい

# bugFix
- edit画面からhome画面に戻る時、navBackを2回くらいタップするとバグる
	- 多分コルーチン処理かなんか上手いことやると対応できるかもしれない
- todoのaddしてもentryBottomSheetのstate保持がリセットされない
	- viewModel関数はあるんで実行させればいいだけのはず
- bottomSheetの入力が深くなるとキーボードに隠れてしまう
- edit画面、entry画面含めてキーボード入力で

# 実装したいものリスト

## tier0

- ボトムのボタンにソート機能をつける
- edit画面のレイアウトをもう少し凝る
	- デフォルトのTodoアプリに寄せたい
- アプリアイコン作成
- リリース
- アイコン長押しで機能説明をポップアップ

## tier1

- githubActionsを使う（CI/CD関係）
- アクセシビリティ対応
- チュートリアル
- 長押しでtodoDone
- 削除時にスナックバーで完了を元に戻せる
- 重要度区別 or カテゴリ分け
- テスト書く

## tier2

- slackみたいなバブル機能
- 通知
- カレンダー連携

通知
アイコンだけのシンプルと、機能の説明が書かれたノーマルモードを作成する
一覧表示をつける
もちろんカテゴリごとの区別ができる機能をつける
デザインはgoogleToDoに寄せる
カテゴリ表示を文字と色で表現
googleとの連携
アカウント登録
カレンダー
todo連携
設定画面は作らない
タッチ一つでタスクの完了と、ミスタッチ時の対応
slackのバブル使いたい！

# 用件定義メモ

[やりたいことリスト](https://www.figma.com/file/ngTGe6cN27iUqRmTcuYLc3/todo_app-%E6%A9%9F%E8%83%BD%E9%9D%A2?type=whiteboard&node-id=122%3A10564&t=g7JjTf5vqkocsi77-1)

# 設計メモ

[appDesign.md](appDesign.md)
