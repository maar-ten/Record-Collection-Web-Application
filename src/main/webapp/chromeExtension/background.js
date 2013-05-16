var regExp = /http:\/\/www.discogs.com\/.+\/release\/([0-9]*)$/i;

function checkForValidUrl(tabId, changeINfo, tab) {
    if (regExp.test(tab.url)) {
        chrome.pageAction.show(tabId);
    }
}

// listen for any changes to the URL of any tab
chrome.tabs.onUpdated.addListener(checkForValidUrl);

function redirectToWebApp(tab) {
    if (regExp.test(tab.url)) {
        var discogReleaseId = regExp.exec(tab.url)[1], tabIndex = tab.index + 1;
        if (discogReleaseId !== undefined) {
            chrome.tabs.create({
                "url": "http://localhost/discogs/release/" + discogReleaseId,
                "active": true,
                "index": tabIndex
            });
        }
    }
}

// listen for click action
chrome.pageAction.onClicked.addListener(redirectToWebApp);