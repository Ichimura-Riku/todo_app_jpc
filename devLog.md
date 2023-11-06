# 技術書展用のメモ

書きたい内容

- room実装
- hiltの導入と解説

## 下書き

### JetpackComposeのTodoアプリにDI（Hilt）を導入してみる

市村凌久

アプリ開発初心者の私がHiltの導入に挑戦しました。ドキュメントも少ない中でなんとか実装を進めていったので、参考になればと思います。

### DIとは

参考: https://the-simple.jp/what-is-di-dependency-injection-one-of-the-software-design-patterns

依存関係の注入のことです。依存関係とは、オブジェクト指向プログラミングにおける、クラスやインターフェースの継承やコンストラクタの参照渡しなどによって構築されます。こうした依存関係はアーキテクチャ設計に則ると、プログラムの仕様変更や機能追加によって影響を及ぼす範囲を狭められるほどいいとされています。具体的な例はandroidDevelopersのMVVMを説明したドキュメントに記載されています。クラスだと複雑なのでただの関数を例にすると、関数の中で変数を低意義するより、引数を使って処理を書いたほうが柔軟性があると思います。これのオブジェクト指向バージョンです。ただ、オブジェクト指向でこうした設計をするには非常に手間なことが多いです。その作業の一部を自動化させて楽しようというコンセプトがこのDIになります。

### 題材とするアプリ

分かりやすくAndroidDevelopersのドキュメントにあるRoomの実装を改造してHiltの導入を行っていきたいと思います。

# 実装ログ

最初に入れたい機能とアプリの概要を決めた

目標はリリースで高品質なアプリを目指した

現役エンジニアの方と相談しながらアプリで使用するライブラリの相談をした

基本的なレイアウトを先に作成して、roomの開発を進める

hiltのところで、オブジェクト指向の理解不足がかなり響いてきている

# roomの実装

tutorialを見ようみまねで実装しようとしたけど上手くいかなかったのでまずはtutorailの内容を丸写ししてそこから合わせていくことにした

並行してhilt, lintとかのツールを導入していった

これがだいぶ厄介で、難しいことを同時にやろうとしてかなりつまづいている

roomの実装自体はできて、あとは合わせていくだけ

この段階まで持っていくのにすら苦労した

## 実装面

### ViewModel内の`savedStateHandle`

このプロパティは一見意味がなさそうだが、画面回転した時のデータ保持なんかを担ってくれる便利屋なんで引数に入れる！

### **mutableStateOf**

uiStateの変更があったときにUIに反映させるための関数

```kotlin
var itemUiState by mutableStateOf(ItemUiState())
    private set

```

### Daoの`@Insert(onConflict = OnConflictStrategy.IGNORE)`

競合が発生した時にその処理を無視する記述

## 機能面

### provider

ここの部分をHiltにやってもらう感じになる。基本的にinterfaceとかを継承したクラスののコンストラクタを渡すために宣言している。目的はUI層との分離で、テストをやりやすくしたり機能の変更を簡単にしたりする目的があってわざわざproviderを入れている。そのまま書くならViewModelとかの内部にコンストラクタを生成すればいいんだけど、そうすると低レイヤーの変更がこうしたViewModelクラスまで変更していかないといけない。たとえば、コンストラクタの引数とかを変更すると一気に影響が出る。こうした問題を解決するためにproviderがあって、そのめんどくささを解消するためにHiltがある。

## エラー対処

### tutorialの実装したアプリ画面が出てこず、エラーでビルドはできるけど即終了する

Surfaceを入れるのを忘れていた

hiltの設定をミスっていた

### hiltが導入したが動かない

いくつかのフェーズがあり、それぞれでうまく設定できていないと動かせない

（そもそもhiltがdaggerの内包していて、わからない部分はdaggerを参考にして実装するとよかった。）

- hiltはkaptでしか動かせなかった（2023/09/06時点で、最新のアルファ版でようやくkspで動かせるようになったらしい。。。）。
- 現役エンジニアの方はAndroid developerのドキュメントではなく、daggerのドキュメントを参考にしていた
- kspが使えるところは使って、daggerだけkaptを併用する形で実装した
	- この際、kspとkaptのバージョンの組み合わせでうまく動かないものがあるので気をつける。自分の場合、

  `id("com.google.devtools.ksp") *version* "1.8.10-1.0.9" *apply*　false
  id("org.jetbrains.kotlin.kapt") *version* "1.9.１0"`
  の２つでエラーが発生したので、

  `id("com.google.devtools.ksp") *version* "1.9.0-1.0.13" *apply* false　id("org.jetbrains.kotlin.kapt") *version* "1.9.0"`

  と修正しました。

  でもどうせなら新しい機能を使ってみたい！（フラグ）

- 簡単な個人的理解として、interfaceの継承を自動でやってくれるためのもの
	- 便利になる点としては、テストコードを導入しやすくなったり、可読性が上がる（らしい）
- プラグインを手当たり次第に対応していた時のやつで`// kapt("groupId:artifactId:version")` のartifactId:
  versionが当たってしまった…

### hilt導入の個人的理解

1. Applicationクラスを作成して、`@HiltAndroidApp`をつける
2. 依存関係注入を行う所に`@AndroidEntryPoint`などのアノテーションを入れる
   （クラスによってアノテーションは変化するかも）
3. メインの使用用途はinterfaceの継承とコンストラクタをUIクラスに影響を与えない程度に分離させつつテストの汎用性を持たせる所になる。
4.
5. のために、`@module` と`@InstallIn` のアノテーションをつけたmodule用のクラスを作成する
6. `@InstallIn` にはシングルトンクラスなどのアノテーション属性を指定する。
   → @InstallIn(SingletonComponent::class)など（singletonしか知らない…）

```kotlin
@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideItemsRepository(provideContext: Context): ItemsRepository {
        return OfflineItemsRepository(InventoryDatabase.getDatabase(provideContext).itemDao())
    }
}
```

### daoのプロパティ型があってない

`/Users/ichimura/StudioProjects/todo_app_jpc/app/build/tmp/kapt3/stubs/debug/com/example/todo_app_jpc/data/TodoDao.java:19: エラー: Type of the parameter must be a class annotated with @Entity or a collection/array of it.
kotlin.coroutines.Continuation<? super java.lang.Void> $completion);`

試したこと

- @Query にsuspendをつける
- 関連定義ファイル全てでQueryをvoidに設定

あインストールで解決できる時がある

バージョン違いでなんかあるらしい

`@Database(entities = [Todo::class], version = 1, exportSchema = false)`

build > clean project

insert

解決！

kaptのバージョンが対応してなくてエラーが出た

1.8.10で動きました！

## コルーチンの処理を実装

コルーチンはスレッド処理でメインスレッドに影響を及ばさないような並列処理を実装してくれる

```kotlin
withContext(Dispatchers.IO) { /*動作させたい処理や関数 */ }
```

flowが返り値の時はこっちも有効

```kotlin
/*<該当する関数() >.flowOn (Dispatchers.IO) */
```

### flowとワンショット（suspend）の使い分け

flowは連続的にデータが変化して、それをUIに反映させたい時に使うといいらしい

# 実装工程

- navigationの実装
	- graph
		- MyAppViewとMainBodyの違いと関係性について精査する
		- 一旦todoAppの構造から精査しないといけないかも
			- はじめはnavigationいらないと思って設計してた故のTodoApp
			- navigationが必要となったので、一旦はmainBodyとTodoAppの統一から始めた方がいいかも知れない
			- 多分、TodoAppの中身をMainBodyに入れればいいと思われる
			- めんどくさいポイントは、コード量が増えてること
			- 結果
				- mainBody
					- homeBody
		- 変更点
			- MainActivityの起動関数変更
			- todoAppの画面に関するものはMainBodyScreenに移動
			- 状態管理変数を実装
			- viewModelを引数にする必要がある
			- 元々呼び出されていた部分との差分があるかを確認する
			- mainBody ViewModelの移植作業
			- 軽く変えてみたけど、結局上手く行かないくさい
			- publicにしろ！的な文章が出てきたのでprivateなのが原因
				- 今は連結作業を進める
				- 連結はできた気がする
				- きた！ｳﾚｼｲ！
				- 依存関係整理してなんとかエラーがない状態を作れた
				- けど、クラッシュした！
				- やっぱりgetShowBottomSheetの関数とかが原因のようだ…
				- 何だか知らんが通った！
				- 原因はprivate変数なのに_がついてなかったことらしい。これつけたらビルドできた！
				- とりあえず進展。タスク一つ消化
				- コミットするか
		- めンンンンどくさ
	- destination
	- mainActivity
	- homeのnavhost object
	- 動作確認
- detailの実装
	- ViewModelの連結をする
	- destinationの実装
		- barに適応
		- navigationに適応
		- クリック関数を追加
	- screen
		- navigationの処理を実装する
			- 書いた
			- ビルドエラー
			- navigationの部分でエラーが起きた
			- クリックイベントの部分がうまく指定できてないと仮定して確認する
			- 予想は引数をうまく指定してないんじゃないかっていう
			- そもそもなんであの書き方で動くのかよくわからんかったし、一回よくみてみるか
			- navGraphの方のcompose関数の引数がrouteだったので、routeIdArgsに変更したらなんか動いた！よくわからんが動いたからヨシ
	- viewModel(写すだけ）
- deleteの実装
	- 削除ボタンを作成
		- 戻る時のボタンも必要だった
		- とりあえずnavControllerを持たせた
		- https://tech.mokelab.com/android/compose/app/navigation/topAppBar.html
	- 確認画面を作成
		- 今detailとeditで分かれてるけど、これ一緒でいい気がする
		- 一旦、detailをeditに寄せるか？
		- 逆かもしれない
		- 逆にして、ビルドできるか試す？
		- 普通に消して名前変えた方が早そう
		- それでいく
		- 今、ViewModelに必要なものがいくつかあるからそれをまとめる
			- TextFieldに入力する内容を保持
				- stateを持てばいい
				- これは他から持ってきていいものなのかな？確認する
					- 当たり
				- 多分できた
				- 画面回転にもしっかり対応してるんで大丈夫だと思う。

			- そういえばそもそもデータとってきてなかったわ
				- これやらんといけんね
				- データ取得はコメントアウトしてたやつを消してやる感じだと思う。
				- これ若干優先順位高めかも
			- OKしたときにRoomに保存
				- キタァ！！
				- 実装完了
				- いけてたはずだけど進まなかったのは、使う関数がupdateじゃなくてinsertだったから
			-
	- delete関数の実装
		- この作業に入る
		- topAppBarに入れる
			- [参考](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#TopAppBar(kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Function0,kotlin.Function1,androidx.compose.foundation.layout.WindowInsets,androidx.compose.material3.TopAppBarColors,androidx.compose.material3.TopAppBarScrollBehavior))
		- あと、ボトムに入れる
			- 完了にしたっていうボタンも入れたい
		- 消す時、確認アラート入れる
		- 実装完了
		- homeのfabと同様、stateはViewModelに持たせることで複数のComposable関数で値の変更ができるようにした
- 編集の実装
	- ついでで実装完了してしまった
- 登録プロパティを追加
	- textFieldも合わせに行こうと思ってたけどうまいことデザインを合わせられなかった
	- bottomSheet
		- 詳細
			- これ入れたけど、編集時にtitleと干渉するので対応必須
		- 締切
			- 時間入れてみたけど、roomに対応させる時のparseが沼
			- dateとtimeを入れる必要がありそう
			- このとき、処理をdate -> timeっていうふうに動かしたい
				- パターン
				- 入力を全てキャンセルしたい時
				- timeまで行って、dateを修正したい時
				- timeまで行って、dateの入力を含めてキャンセルしたい時
				- 全て入力したい時
				- timeは入力したけど、dateを修正してそのままdoneしたい時
			- 多分、実装はこんな感じ
				- 画面外タッチは入力を全てキャンセル
				- ボタンとしてキャンセル、time(date)へ、OK（date or timeがあれば出現)
				- メモ
					- 管理するのはtimeとdateの入力情報で、ない時はnullで判定する。
					- dateだけ -> その日の23:59を締切に
					- timeだけ -> 当日のその時間を締切に
					- まずはない時とある時の状態を管理できるようにする。
					- どこでstateを管理するか確認しつつ実装
					- 再代入の可能性があるので、stateはviewModelでやっぱり保持するべきかもしれない
					- 大体できてきた
					- deadline data classが複雑すぎてわからん
					-
                  updateメソッドを実装してないのに、なんでviewModelで保持する値を更新できてるのか分からなかったけど、DatePickerStateっていうクラス自体がupdateするメソッドを持っていて、このクラスだけ特別実装しなくても更新することができた。
					- あとは入力された時間を表示すれば締切登録はOK
					- roomへの登録もあった
					- 細かいやること
			- 残りのやること
				- 時刻の表記をフォーマット
				- chipsのアイコンを修正
				- 締切登録するときのアイコンを修正
				- closePickerの変数名を修正
				- timePickerComponentをもっとコンパクトにする（datePickerと同じにする）
				- chipsを削除できるようにする
					- 意外と面倒だった
					- composeをレンダリングしたいタイミングでviewModelのstateが変化してくれない
					- 他のプロパティを参考に実装したい
					- contentTextFieldの値はmutableStateで保持しているから、一旦これを真似してみよう
					- コミットしたからいくらでもいじれる
						- 不安要素
						- mutableState未実装の理由を思い出せないけど、根幹の実装から変わらないでほしい
						- data classでの実装がダメだとしたら今日はもう立ち直れない
						- あと、viewModelの関数の順番とかをformatしたい。ぐちゃぐちゃすぎて分からない
						- コメントアウトとか、しっかり弾いていきたい。
						- 確認したら、一番上のdeadlineUiStateがmutableになってる。
						- このmutableが下の方までいっていたとしたらmutable以外のところを変える必要がある。
						- なんか一応できた。よく分からんが動いた状態なので、あとで検証していく
						- 学び
							- data
							  classのプロパティに関連した関数は、Uiに反映させたい変数を取り出したり更新したりする処理をViewModelクラス内で記述するべき
							- UiStateに使うプロパティは、一番深い値（使用したい直接のプロパティ）を直接mutableStateで定義する？
							- Stateの更新と取得のタイミングでヌルポが発生してクラッシュするケースがあるので、気を付ける
							- クラスを入れ子構造にするデータクラスのインスタンスをmutableStateにすると、入れ子のクラスもmutableになる
						- 検証事項
							- rememberTimePickerStateを使っていない
								- いらないことがわかった
								- timePicker自体が、引数に入れてるプロパティ自体に変更を加えるような処理になっているのかもしれない
								-
                              datePickerは、とりあえず入力された値を保持するためのプロパティが必要な気がしてて、必要だとしてもdatePickerだけでいい
							- rememberDatePickerStateにしたが意味があったのか
								- 結論いる
								-
                              なくても動くけど、この値はEntryされたり、EntryScreenが破棄されたタイミングで同様に消えてもらいたい値なので、このままでいい。と思う。
								- 大体、Uiの保持で問題ない部分なので、どちらにしてもrememberでいい気がする。
								-
                              逆に、rememberじゃない方がいい時は、bottomSheetがhideした時に自動で登録するとか、Uiが破棄されるタイミングでも値を保持しておきたい場合はViewModel保持で良さそう
							- TodoEntryViewModelないで作成していったものにいらないものはあったのか
								- まず、変更したものをまとめないと多すぎて整理できない
								- なんだったら、DeadlineStateデータクラスにも変更を加えたので、両方確認していく
								- deadlineUiState
									- これは結局必要だった。
									- 強いて考えることがあるとしたら、_をつけるかどうかぐらい
									- mutableにする必要があったかどうかはわかんない
									- 結論mutableじゃなくても動く
									- mutableにする理由はUiのレンダリング
									- ここじゃなくて、他のMutableStateの時に検証さらに必要そう
								- deadline(Picker)State
								  ここでまず、一番ややこしかったのが、大元のdeadlineUiStateがmutableだけど、そっから深いプロパティを参照するのがmutableという認識であってるのかを確認する必要がある
									- 結論、一番上がmutableな値なら、そのさらに深いプロパティもmutableになる
									- ということは、細かく実装してきたmutableStateはいらないっていうことになる
							- 他にDeadlineStateデータクラスにいらないものはあるか
								- 現状で最小限の実装な気がする
							-
                          mutableStateをdeadlineUiStateからそれぞれ直接定義したけれど、これらが意味あるのかまたはどちらの実装の方が適切なのか
							- これ上記の検証で実証済み
						- まだ実装続けます
						- コルーチンの対応
							- やってみたけど、クラッシュもせず、けれど終わらずっていう状態になってしまった。あの書き方自体がそもそもダメなのかもしれない。
							- 普通に現役エンジニアの方に質問したいレベルだけどとりあえずチャットGPTに聞こうかな
							- 今日は寝る
							- 聞いたらFlowを使えっていう話だった
								- View層での非同期処理は避けるべき
									- 普通にUIに入れてたね
									- 非同期処理はViewModelの外部でやるべきらしい。
									- まじでどういうこと？
									-
								- UI表示と非同期処理の切り離し
								- UI状態管理を工夫する
									- ここでstateFlowとか、LiveDataっていう単語が出てきた
						- flowの対応
							- ググったらそれらしいドキュメントがあったので、これを一旦触ってみる
							- ぱっと見、アーキテクチャ設計としてそもそも間違っていそうな気がするんで、これを修正しようかな
				- roomへの登録
				- EditScreenにあとから修正する用のUIを実装
			- やりたかったけどできなかったこと
				- ドラムロール24時間表記
					- TimePickerの24Hourっていうプロパティをtrueにしたらできた
					- Time and Date Pickerのdialogで、ボタンを追加したり、レウアウトを変更したかった
						- ボタンの追加はできた
						- 新しい項目を作成するのはできなかった。material3
						  tokensを読み込むことができなくて、一からレイアウトを作成することができなかった。
			- リンク
				- https://m3.material.io/components/time-pickers/specs
				- https://qiita.com/masayahak/items/efd11b86cd4643d2842d
					- https://developer.android.com/jetpack/androidx/releases/compose-material3?hl=ja
					- https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate?hl=ja#types
					- https://cs.android.com/androidx/platform/tools/dokka-devsite-plugin/+/master:testData/compose/samples/material3/samples/TimePickerSamples.kt;l=230;drc=03ca30d22e6ee3483142f2e4048db459cb5afb79
					- https://issuetracker.google.com/issues/288311426?pli=1
					- https://stackoverflow.com/questions/75853449/timepickerdialog-in-jetpack-compose
				- https://developer.android.com/training/snackbar/action?hl=ja
				- https://zenn.dev/yagiryuuu/articles/0ed6108070d8c1
		- 登録した時間
		- 重要度
			- タグの機能だったはず
		-
		- editScreen

- アーキテクチャの対応
	- タイミングが交錯してきたんで、一旦大きい枠として入れておく。できたら、flowの前の部分に入れてflowの作業にはいる
	- memo
		- 今、repositoryが保持するべきデータロジックを全てViewModelの部分に保持している
		- これの役割分担をする必要がある。
		- いわゆるビジネスロジックに該当する部分はリポジトリに構成する必要がある。
		- リポジトリはinterfaceで定義するのしか知らないんで、詳しく調べる必要がありそう
		- 変更する内容としては、viewModelにあるrepositoryにするべき処理を判断して移動して、コルーチン処理の対応をする
		- だから、一旦コルーチンの処理は止めておいて、ブランチ切ってアーキテクチャ対応を進める
	- やること
		- viewModelにある、repositoryで実装するべき処理を判断する
		- repositoryにその処理を移動させて、UIの処理も対応する
		- コルーチン処理の対応を進める
		-
	- わかったこと
		- これまでmodel, viewModel, viewというふうに括ってきたけど、やっぱりrepositoryはmodelで合ってそう
		- アーキテクチャによって防ぎたい要素として、整合性を保つことというのがありそう
			- これは、整合性が保てないかも知れないものは隠して、保てるものだけを公開するというもの
			- この後悔っていうのはまだわからないけどhiltの役割な気がする
			- 整合性を保つっていう面で言うと、repositoryとかで定義したものの中でも公開するもの公開しないものがありそう
		- どうやら、UseCaseっていうのが適切そう
			- repositoryはもちろん、dataからの呼び出しが該当してるのだけれど、もう一つUseCaseっていうのがいいらしい
		- useCase、なくてもいいけどあってもいいよっていうもの
			- ViewModelの中のコードをまとめる、一つのユースケース（ユースケース図をイメージするといいかも知れない）
			- ちゃんと整理しよう
			- このtodoアプリでuseCase化するべきものは何か
				- getDeadlineUiState
			- わかったかもしれない
			- deadlineStateを解体してuseCaseにすれば良さそう
			- 設計の話の脳内整理
				- repositoryの処理部分をuseCaseに持って行こうとしたら関連する処理自体は少なかったんでそのままでいいかも
				- show関連の関数も、基本的にはUIデータになりそうで残してて良さそう
				- useCase自体、viewModelの分割とも捉えられる。しかし現状はfat
				- fatの部分の大部分がdeadlineState
				- DeadlineStateで保持しないでTodoEntryかuseCaseに保持する？
				- 値更新のための関数で、省略するget() =みたいな処理をかけばupdate関数もいらない？
					- 一旦これ試してみよう
					- それで設計的にどっちの方が安全なのかも検討する
					- パッと調べた感じ、結局update関数が必要そうではある
				- isShow**State的な要素はViewModelじゃなくてUIで保持できるし、保持した方がいい
					- ビジネスとジックとUiロジックを意識していなかった
						- ブックマークとかのアプリの要件となるのがビジネスロジック
						- クリックした時の画面移動や、ナビゲーションなどがUIロジック
					- ただ、timePickerとdataPickerの絡みがあるんで、上の階層で管理した方が良さそうではある
					- stateのsetはviewModelの方が良さそう
					- あと、isInput**もUI層で良さそう
					- data classで結局まとめるのは良さそう
					- だけど、UIで持つべきものもあるんで一旦解体した方がいいかもしれない
					- それでも、とりあえずは細かくひとつずつ入れていこう
					- ふざけんな結局ViewModelいるやんけ！
					- useCase不要そう
				- 後日談、というか今回のオチ
					- fatViewModelの解決としてUseCaseが有効なのは事実
					- しかし、その内部はRepositoryを用いるDataレイヤと繋げる役割を含めるビジネスロジックが該当する
					- そのため、今回UIStateが保持されないor同期できないもんdないはViewModel内でコルーチン処理をするのが適当そう
					- 厄介だったのが、コルーチン処理の説明でUseCaseが出てきたのも混乱の原因
					- ただFlowの実装はどのみち必要だとは思うんで、進める
				- 処理順
					- okおす
					- datePickerの値をviewModel内のdatePickerStateに反映させる
					- 反映を待って、完了したら、
					- viewに移すためのdatePickerUiViewState作成する -> getDeadlineUiViewState()
						- suspendにする必要はなくて、stateの更新にともなって実行されるべき
						- というか、awaitするならupdateDatePickerStateの方だと思う
					- flowで変化を確認する
					- 変化があったらUI用のString作成(screen)
				- つまり
					- datePickerはdeadlineState（ViewModelが持つstate）の変化を待つ
						- deadlineStateを非同期処理にするよりも、Flowにしてやった方が適切かも
					- 変化されたらUIViewを作成するViewModel関数を実行
					- Screenの方でもUiViewの変更を監視して、変化したら値を反映させる
				- datePickerStateは完了としよう
				- 次にTimePickerState！
					- updateTimePickerState?があればこれを非同期に
					- datePickerUiViewStateの非同期処理
		- 現在の実装の問題点
			- fatViewModelになっている
			- fatな部分
				- state管理の数
					- 本来ならもっと少なくていいはず
					- それこそ、update関数はget()の物で賄えている気がする
					- サンプルコード参考にすると、そんな感じがする
					- これは要確認
				- stateの管理にしても、特にdeadlineStateがデカすぎる
					- さらにいうと、getDeadlineUiStateがクソでかい
					- これはuseCaseに持っていくことができるのか
					- flowへの変換をどこでやって、viewModelとかUIコンポーネントにどうやって持っていくのか考える必要がある
		- useCase使いながら、FlowとかLiveDataを組み込んでいきたい
		- サンプルからは、repositoryからFlowで、viewModelからLiveDataとかだった気がする
		- 将来的な実装プラン
			- 安全なアーキテクチャに則った設計を進めつつ、FlowやLiveDataによるstate管理を進めて、そのstateに対してCoroutinesの対応を進める
- editのdeadlineStateをInputChipで表示する
	- editViewModelにentryViewModelと同じ実装をするのかな？
	- 他のTextViewはもう同じようなことできてるから、一旦他の実装と比べてやってみる
	- 実装の違いを見つけた
  ```    
  var todoUiState by mutableStateOf(TodoUiState())
        private set
  ```
  と

  ```
	　init {
	  　iewModelScope.launch {
	  　　todoUiState = todoRepository.getTodoById(itemId)
	  　　.filterNotNull()
	  　　.first()
	  　　.toTodoUiState()
	  　}
	  }
  ```
	- これの違いについて確認したいかもしれない
	- gptに聞いてみた
	- こっちはeditViewModelと違ってRoomからデータを取得するフェーズがあるのでこういう実装になっている。
	- toTodoUiStateを実行する必要があって、そのためにはinitで実装する必要がある。
	- いつ読み込むの？っていう話になりそう
	- 定義の時に読み込んでおかないとscreenの破棄タイミングの関連でバグの原因になりそう
	- わざわざViewModelScopeで動かしてるし、これが表示されないと画面が映らないっていうのはよくない
	- 別に定義の時に読み込んで置かないとっていうことでやってるわけじゃなさそう
	- initの有無の違いについて
		- なるほど、データのロードと初期化のタイミングでデータがない可能性がある。
		- 多分、別の変数定義とか関数で対象の値を使用して行う場合、その値がnullなんで定義できない事象が起こる
		- これをinitなしで解決するには全て非同期処理とかawaitの処理を加える必要がありそう
		- 必要な部分だけinitで先に定義してあげることでviewModelもとい、viewの生成を円滑にできる。
		- いや、ちょっと認識が違った
		- initで使う変数は、init外で定義して使われているんで、何よりも先にinitが実行されるっていうわけではないらしい
		- 認識はコルーチンとか、await()の別の実装方法くらいしかわかんない
	- 使い分け
		- init ブロックの使用:
			- 初期化時に非同期処理を行う必要がある場合、非同期処理を行いつつ待機できる場合に有用です。ただし、データの不整合に対するリスクを把握した上で使用する必要があります。
		- await() や suspend 関数、CoroutineScope の使用:
			- 非同期処理を行う場合に推奨される方法です。特に非同期データを利用するタイミングでの安全な待機と処理を行う場合に使用します。
		- ぶっちゃけよくわかんない
	- わかったかもしれない
	- そもそも、awaitは違うかもしれないけど、コルーチンは併用して使うもの
	- だから、変数定義の際にちょっと待って欲しい時に欲しいやつかもしれない
	- 比較するとしたらawaitとかとがいいかも
	- awaitも違った。
	- やっぱり、クラスの初期化っていう部分である
	- けど、優位性の理解が難しいと感じる
	- 実際に消して理解した
		- 元々、無条件でコルーチンが使えるわけじゃない。
		- 関数化して、suspendにしてっていうステップを踏まないとコルーチンは使えない
		- **コルーチンが使えるそれ以外のアプローチでviewModelの初期値を定義する時に使用できるという理解をした**
	- 実装
		- 普通にコピペしようとしたら問題が発覚した
		- というのも、Picker系統のコンポーネントを表示するためにはViewModelを渡す必要がある
		- これまでViewModelはEntryViewModelを指定して引数を指定していた。
		- けど、今回はEditViewModel
		- ここで実装方法をいくつか思いついた
			- EditにそのままEntryの値を保持できるように書き換える
			- その場合、PickerStateの引数とかを大きく変える必要がある
				- プラン１、interfaceを実装してPickerComponentの引数の数を抑える
					- viewModelに継承させたいが、同様の実装が増えることを仮定すると深い依存関係を構築することが予想できる
					- 深いと実装時の可読性は悪いし、ソースコードも冗長な記述になりそう
					- 実装の手段としてinterfaceを使うことはいいと思うが、そのままentryやeditのviewModelに継承するというのはあまり良くない気がする
				- プラン２、Stateの保持をUI層で行う
					- そもそもアプリの設計としてよくないのは明白
					- 状態をUI層で持つということは、画面回転とかの急なActivity再構築に対応できないのでよくない
					- やっぱりstate保持はViewModel層で実装することはmustのはず
					- なし
				- プラン３、引数の引き渡しの数を増やして、呼び出し元で必要な形状に変形させてから渡す
					- 引数の数を増やすこと自体は問題ない
					- ただ、その引数に渡すためのソースコードが増えるのが問題だと思っている
					- 引数を参照渡しにするにはClassの型を指定する必要がある
					- それぞれのStateをそうやって管理するにはDataClassで区分けする必要がありそう
					- 引数を分ける際、どのように分けるのかを実際に考えてみる
						- dataClassのdeadlineStateでやればいけるかもしれない
						- けど、前にその実装は理由があってやめた気がする。
						- どっかにある気がするけど見つけられない
							- 多分思い出した
							- viewModelとクラスとして保持するのでは、結構違う
							- ViewModelないの変数にdataClassとして定義した関数からアクセスして編集するのができない説
							- 全てクラス内のStateで管理できるようなら問題ない気がする。
							- 一旦ブランチ切って挑戦してみるか？
						- もう一度、dataclassの実装で問題ないか確認したい
						  あとで
				- rememberを上の階層でもつ

- タイル長押しで削除も追加
- 登録する時の他の要素も追加できるようにする
- ボトムメニューバーに機能を持たせる
- カテゴリの作成
- slide sheetでbottomSheetを実装してみる