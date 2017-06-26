/**
 * Created by Chunwei on 2015/11/25.
 */
var presentApp = angular.module("presentApp", []);

presentApp.controller("presentController", function ($scope, $http) {
    $scope.isShowDetail = false;
    $scope.dataList = [];

    $scope.errorMessage = "";
    
    $scope.warnMessage = "";

    $scope.presentAttr = {
        'user_name': '',
        'password': '',
        'present_type': '100301',
        'sent_reseon_code': '1',
        'cycle': '12',
        'times': '1',
        'start_cycle': '201511',
        'remark': ''
    };

    $scope.delData = function () {
        var tempList = [];
        angular.forEach($scope.adjustList, function (val) {
            if (!val.isChoose) {
                tempList.push(val);
            }
        });
        $scope.adjustList = tempList;
    };

    $scope.calData = function () {
        $scope.adjustList = [];
        $scope.isShowDetail = false;
    };

    $scope.doData = function () {
        var adjustDataList = [];
        angular.forEach($scope.dataList, function (val) {
            var data = {
                "username": $scope.adjustAttr.user_name,
                "password": $scope.adjustAttr.password,
                'presentType': val.present_type,
                'sentReseonCode': val.sent_reseon_code,
                'cycle': val.cycle,
                'times': val.times,
                'start_cycle': val.start_cycle,
                'remark': val.remark
            };
            adjustDataList.push(data);
        });

        $scope.adjustList = adjustDataList;
        $scope.isShowDetail = false;
    };

    $scope.doPostData = function () {
        if ($scope.adjustList.length > 0) {
            $http.post("api/adjust/add", $scope.adjustList).success(function (data) {
                if (data == "提交成功") {
                    $scope.setMessage("warn", "提交成功");
                }
                else {
                    $scope.setMessage("error", data);
                }

                $scope.adjustList = [];
            }).error(function () {
                scope.setMessage("error", "接口调用异常");
            });
        }
    };

    $scope.setMessage = function (type, context) {
        if (type == "error") {
            $scope.errorMessage = context;
        }
        else if (type == "warn") {
            $scope.warnMessage = context;
        }
    };

    $scope.clearMessage = function () {
        $scope.errorMessage = "";
        $scope.warnMessage = "";
    };

    //上传后
    var upLoaded = function (evt) {
        $scope.adjustList = [];
        var fileString = evt.target.result;
        var adjustDataList = fileString.split("\r\n");

        $scope.$apply(function () {
            angular.forEach(adjustDataList, function (val, idx) {
                if (idx != 0) {
                    var arr = val.split(",");
                    var serialNum = arr[0];
                    var fee = arr[1];

                    if (serialNum != "") {
                        var adj = {
                            "id": (idx % 3) - 1,
                            "isChoose": false,
                            "serialNumber": serialNum,
                            "fee": fee
                        };
                        $scope.adjustList.push(adj);
                    }
                }
            });

            $scope.isShowDetail = true
        });
    };

    //change事件
    var updateUploadFile = function (evt) {
        var $target = $(evt.target);
        var path = $target.val();
        var file = path.match(/[^\/\\]+$/gi)[0];
        var filterType = 'csv';
        var rx = new RegExp('\\.(' + (filterType ? filterType : '') + ')$', 'gi');

        //过滤文件
        if (file.match(rx) == null) {
            alert('请上传csv文件');
            return;
        }

        var files = evt.target.files; // FileList object
        if (files[0]) {
            var reader = new FileReader();
            reader.readAsText(files[0]);
            reader.onload = upLoaded;
        }
    };

    $('#uploadfile').bind('change', $.proxy(updateUploadFile, this));
});